package org.storm.commons.offlinetask.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.storm.commons.offlinetask.ability.OfflineTaskExecAbility;
import org.storm.commons.offlinetask.common.*;
import org.storm.commons.offlinetask.dao.entity.OfflineTaskEntity;
import org.storm.commons.offlinetask.dao.entity.OfflineTaskParam;
import org.storm.commons.offlinetask.domain.OfflineTaskDTO;
import org.storm.commons.offlinetask.domain.TaskExecResult;
import org.storm.commons.offlinetask.enums.TaskStatusEnum;
import org.storm.commons.offlinetask.exception.BusinessException;
import org.storm.commons.offlinetask.exception.SystemException;
import org.storm.commons.offlinetask.service.OfflineTaskExecFactory;
import org.storm.commons.offlinetask.service.OfflineTaskManageService;
import org.storm.commons.offlinetask.service.OfflineTaskScanService;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
@Slf4j
public class OfflineTaskScanServiceImpl implements OfflineTaskScanService {

    @Autowired
    private OfflineTaskManageService offlineTaskManageService;

    /**
     * job锁的key
     */
    private static final String JOB_LOCK_KEY = "MY_SCAN_TASK_JOB";
    /**
     * 缓存里负责存储"系统正在执行中的任务"的集合key
     */
    private static final String MY_EXECUTING_TASK_SET = "MY_EXECUTING_TASK_SET";
    /**
     * job锁失效时间
     */
    private static final long JOB_LOCK_EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * 扫描任务并执行
     */
    @Override
    public void scanTask() {
        log.info("----------------scanTask()开始----------------");
        Jedis jedis = this.getCacheClient();

        //加一个任务锁，当前时刻只有一台机器在执行这个扫描工作
        String requestId = UUID.randomUUID().toString();
        boolean tryGetDistributedLockSuccess = false;
        try {
            tryGetDistributedLockSuccess = this.tryGetJobLock(jedis, requestId);
            if (!tryGetDistributedLockSuccess) {
                log.info("job没有获取到锁，任务返回");
                return;
            }
        } catch (Exception e) {
            log.error("获取job的锁异常，任务返回，原因：{}", e.getMessage(), e);
            this.releaseJobLock(jedis, requestId);
            return;
        }

        try {
            List<OfflineTaskEntity> taskEntityList = this.getTaskEntityList();
            if (CollectionUtils.isEmpty(taskEntityList)) {
                log.info("无待执行的离线任务，任务返回");
                return;
            }
            this.checkTask(taskEntityList);
            //检查执行列表里，是否存在这几个任务，如果存在，那么本次扫描就不做操作
            List<OfflineTaskEntity> toBeExecuteTaskList = this.minusExecutingTask(taskEntityList);
            if (CollectionUtils.isEmpty(toBeExecuteTaskList)) {
                log.info("查询出的任务都在执行中，任务返回");
                return;
            }

            //异步执行
            ExecutorService executor = ThreadUtil.Pool.COMMON.getExecutor();
            for (OfflineTaskEntity offlineTask : toBeExecuteTaskList) {
                executor.execute(() -> {
                    try {
                        this.process(offlineTask);
                    } catch (Exception e) {
                        log.error("业务逻辑执行过程出现异常，原因:{}", e.getMessage(), e);
                    }
                });
            }

        } catch (Exception e) {
            log.error("执行过程中出现异常，原因：{}", e.getMessage(), e);
        } finally {
            this.releaseJobLock(jedis, requestId);
            log.info("----------------scanTask()结束----------------");
        }
    }

    private Jedis getCacheClient() {
        Jedis jedis = new Jedis("localhost", 6379);
        return jedis;
    }

    private void checkTask(List<OfflineTaskEntity> taskEntityList) {
        for (OfflineTaskEntity task : taskEntityList) {
            if (task == null) {
                throw new BusinessException("task为null");
            }
            if (task.getId() == null) {
                throw new BusinessException("taskId为null");
            }
            if (task.getTaskType() == null) {
                throw new BusinessException("taskType为null");
            }
            if (task.getTaskStatus() == null) {
                throw new BusinessException("taskStatus为null");
            }
        }

    }

    private List<OfflineTaskEntity> getTaskEntityList() {
        OfflineTaskParam queryOfflineTaskParam = new OfflineTaskParam();
        queryOfflineTaskParam.setTaskStatus(TaskStatusEnum.DAI_ZHI_XING.getCode());
        queryOfflineTaskParam.setPageNum(1);
        queryOfflineTaskParam.setPageSize(3);
        Page<OfflineTaskEntity> taskPage = offlineTaskManageService.selectTaskPage(queryOfflineTaskParam);
        return taskPage.getContent();
    }

    private boolean releaseJobLock(Jedis jedis, String requestId) {
        boolean result = RedisTools.releaseDistributedLock(jedis, JOB_LOCK_KEY, requestId);
        log.info("releaseJobLock()结果：{}", result);
        return result;
    }

    private boolean tryGetJobLock(Jedis jedis, String requestId) {
        boolean tryGetDistributedLockSuccess = RedisTools.tryGetDistributedLock(jedis, JOB_LOCK_KEY, requestId, JOB_LOCK_EXPIRE_TIME);
        log.info("tryGetJobLock结果：{}", tryGetDistributedLockSuccess);
        return tryGetDistributedLockSuccess;
    }

    /**
     * 处理逻辑，改成全异步
     *
     * @param taskEntity
     */
    private void process(OfflineTaskEntity taskEntity) {
        try {
            //检查该任务是否已经在缓存里
            if (this.taskIsExecuting(taskEntity)) {
                log.info("该任务[id:{}]已经在执行中", taskEntity.getId());
                return;
            }
            //把当前任务放到缓存的待执行列表里
            this.lockTask(taskEntity);
            //设置状态为执行中
            this.updateDbStatusZhiXingZhong(taskEntity);
            OfflineTaskExecAbility execService = OfflineTaskExecFactory.getTaskType(taskEntity.getTaskType());

            Response<TaskExecResult> response = execService.executeTask(this.convertToDto(taskEntity));//去执行

            //根据执行结果，设置状态为执行成功或失败
            if (ResponseCodeEnum.SUCCESS.code().equals(response.getCode())) {
                //设置状态为成功
                this.updateDbStatusChengGong(taskEntity);
            } else {
                //设置状态为失败
                this.updateDbStatusShiBai(taskEntity);
            }
            this.sendMq(response);
        } catch (Exception e) {
            log.error("任务执行过程中发现异常，原因：{}，当前线程名[{},(线程id：{})]", e.getMessage(), Thread.currentThread().getName(), Thread.currentThread().getId(), e);
            //设置状态为失败
            this.updateDbStatusShiBai(taskEntity);
            throw new SystemException(e);
        } finally {
            //完成任务后去掉
            this.unlockTask(taskEntity);
        }
    }

    private void sendMq(Response<TaskExecResult> response) {
        //后续实现，现在不用
        log.info("发MQ，目前不用");
    }


    @Override
    public void reDoTask(Long taskId) {
        try {
            OfflineTaskEntity taskEntity = offlineTaskManageService.selectById(taskId);
            if (!TaskStatusEnum.SHI_BAI.getCode().equals(taskEntity.getTaskStatus())) {
                log.info("该任务的状态不是失败状态，不可重新执行");
                return;
            }
            ExecutorService executor = ThreadUtil.Pool.COMMON.getExecutor();
            executor.execute(() -> process(taskEntity));
        } catch (Exception e) {
            log.error("reDoTask()出现异常,原因:{}", e.getMessage(), e);
        }
    }


    private void updateDbStatusShiBai(OfflineTaskEntity offlineTask) {
        Long taskId = offlineTask.getId();
        Boolean success = offlineTaskManageService.updateStatus(taskId, TaskStatusEnum.SHI_BAI.getCode());
        if (!success) {
            throw new BusinessException("任务[id:" + taskId + "]更新状态为[" + TaskStatusEnum.SHI_BAI.getDesc() + "]时失败");
        }
    }

    private void updateDbStatusChengGong(OfflineTaskEntity offlineTask) {
        Long taskId = offlineTask.getId();
        Boolean success = offlineTaskManageService.updateStatus(taskId, TaskStatusEnum.CHENG_GONG.getCode());
        if (!success) {
            throw new BusinessException("任务[id:" + taskId + "]更新状态为[" + TaskStatusEnum.CHENG_GONG.getDesc() + "]时失败");
        }
    }

    private void updateDbStatusZhiXingZhong(OfflineTaskEntity offlineTask) {
        Long taskId = offlineTask.getId();
        Boolean success = offlineTaskManageService.updateStatus(taskId, TaskStatusEnum.ZHI_XING_ZHONG.getCode());
        if (!success) {
            throw new BusinessException("任务[id:" + taskId + "]更新状态为[" + TaskStatusEnum.ZHI_XING_ZHONG.getDesc() + "]时失败");
        }
    }

    private boolean taskIsExecuting(OfflineTaskEntity offlineTask) {
        String taskId = this.getCacheValue(offlineTask);
        Jedis jedis = this.getCacheClient();
        return jedis.sismember(MY_EXECUTING_TASK_SET, taskId);
    }

    private void unlockTask(OfflineTaskEntity offlineTask) {
        String taskId = this.getCacheValue(offlineTask);
        Jedis jedis = this.getCacheClient();
        Long removeCount = jedis.srem(MY_EXECUTING_TASK_SET, taskId);
        log.info("从缓存[{}]删除任务[id:{}]，返回值:{}", MY_EXECUTING_TASK_SET, taskId, removeCount);
    }

    private void lockTask(OfflineTaskEntity taskEntity) {
        String taskId = this.getCacheValue(taskEntity);
        Jedis jedis = this.getCacheClient();
        Long addCount = jedis.sadd(MY_EXECUTING_TASK_SET, taskId);
        log.info("从缓存[{}]添加任务[id:{}]，返回值:{}", MY_EXECUTING_TASK_SET, taskId, addCount);
    }

    private String getCacheValue(OfflineTaskEntity taskEntity) {
        return String.valueOf(taskEntity.getId());
    }

    /**
     * 从缓存里读现在正在进行的任务，如果有重复的，就去掉重复的
     *
     * @param taskEntityList
     * @return
     */
    private List<OfflineTaskEntity> minusExecutingTask(List<OfflineTaskEntity> taskEntityList) {
        Jedis jedis = this.getCacheClient();
        Set<String> taskNoSet = jedis.smembers(MY_EXECUTING_TASK_SET);
        if (taskNoSet.isEmpty()) {
            return taskEntityList;
        }
        List<OfflineTaskEntity> notExecTaskList = new ArrayList<>(taskEntityList.size());
        for (OfflineTaskEntity task : taskEntityList) {
            if (!taskNoSet.contains(String.valueOf(task.getId()))) {
                notExecTaskList.add(task);
            }
        }

        return notExecTaskList;
    }

    private OfflineTaskDTO convertToDto(OfflineTaskEntity source) {
        OfflineTaskDTO dto = new OfflineTaskDTO();
        BeanUtils.copyProperties(source, dto);
        return dto;
    }
}
