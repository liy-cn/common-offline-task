package org.storm.commons.offlinetask.ability.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.storm.commons.offlinetask.ability.OfflineTaskExecAbility;
import org.storm.commons.offlinetask.common.Response;
import org.storm.commons.offlinetask.domain.OfflineTaskDTO;
import org.storm.commons.offlinetask.domain.TaskExecResult;
import org.storm.commons.offlinetask.enums.TaskTypeEnum;
import org.storm.commons.offlinetask.exception.BusinessException;
import org.storm.commons.offlinetask.service.OfflineTaskExecFactory;

@Service
@Slf4j
public class OrderExportOfflineTaskExecAbility implements OfflineTaskExecAbility, InitializingBean {
    /**
     * 执行任务
     *
     * @param offlineTaskDTO
     * @return
     */
    @Override
    public Response<TaskExecResult> executeTask(OfflineTaskDTO offlineTaskDTO) {
        Long taskId = offlineTaskDTO.getId();
        log.info("离线任务[id:{}]处理开始，入参：{}", taskId, JSON.toJSONString(offlineTaskDTO));
        //你的真实业务流程
        Response<TaskExecResult> response = new Response<>();
        TaskExecResult taskExecResult = new TaskExecResult();
        taskExecResult.setOfflineTaskDTO(offlineTaskDTO);
        response.success(taskExecResult);
        if (taskId == 9) {
            throw new BusinessException("故意抛一个异常");
        }
        log.info("离线任务[id:{}]处理完毕", taskId);
        return response;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        OfflineTaskExecFactory.register(TaskTypeEnum.ORDER_EXPORT.getTaskType(), this);
    }
}
