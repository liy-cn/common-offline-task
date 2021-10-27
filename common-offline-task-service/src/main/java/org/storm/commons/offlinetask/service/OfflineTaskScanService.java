package org.storm.commons.offlinetask.service;

public interface OfflineTaskScanService {
    /**
     * 扫描任务并执行
     */
    void scanTask();

    /**
     * 任务重新执行
     * @param taskId
     */
    void reDoTask(Long taskId);
}
