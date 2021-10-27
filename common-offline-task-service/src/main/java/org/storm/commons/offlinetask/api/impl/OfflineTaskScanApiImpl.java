package org.storm.commons.offlinetask.api.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.storm.commons.offlinetask.api.OfflineTaskScanApi;
import org.storm.commons.offlinetask.service.OfflineTaskScanService;

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
