package org.storm.commons.offlinetask.api;


import org.storm.commons.offlinetask.common.Response;
import org.storm.commons.offlinetask.domain.TaskExecResult;
import org.storm.commons.offlinetask.domain.OfflineTaskDTO;

public interface OfflineTaskExecApi {
    /**
     * 执行任务
     * @param offlineTaskDTO
     * @return
     */
    Response<TaskExecResult> executeTask(OfflineTaskDTO offlineTaskDTO);
}
