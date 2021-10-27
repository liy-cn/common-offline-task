package org.storm.commons.offlinetask.api.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.storm.commons.offlinetask.api.OfflineTaskExecApi;
import org.storm.commons.offlinetask.api.OfflineTaskManageApi;
import org.storm.commons.offlinetask.api.OfflineTaskScanApi;
import org.storm.commons.offlinetask.common.Page;
import org.storm.commons.offlinetask.common.Response;
import org.storm.commons.offlinetask.common.ResponseCodeEnum;
import org.storm.commons.offlinetask.common.ThreadUtil;
import org.storm.commons.offlinetask.domain.OfflineTaskDTO;
import org.storm.commons.offlinetask.domain.QueryOfflineTaskParam;
import org.storm.commons.offlinetask.domain.TaskExecResult;
import org.storm.commons.offlinetask.enums.TaskStatusEnum;
import org.storm.commons.offlinetask.exception.BusinessException;
import org.storm.commons.offlinetask.exception.SystemException;
import org.storm.commons.offlinetask.service.OfflineTaskScanService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
@Slf4j
public class OfflineTaskScanApiImpl implements OfflineTaskScanApi {

    @Autowired
    private OfflineTaskScanService offlineTaskScanService;

    /**
     * 扫描任务并执行
     */
    @Override
    public void scanTask() {
        offlineTaskScanService.scanTask();
    }

    /**
     * 任务重新执行
     *
     * @param taskId
     */
    @Override
    public void reDoTask(Long taskId) {
        offlineTaskScanService.reDoTask(taskId);
    }
}
