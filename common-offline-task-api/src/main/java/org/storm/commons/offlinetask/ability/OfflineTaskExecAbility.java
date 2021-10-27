package org.storm.commons.offlinetask.ability;


import org.storm.commons.offlinetask.common.Response;
import org.storm.commons.offlinetask.domain.TaskExecResult;
import org.storm.commons.offlinetask.domain.OfflineTaskDTO;

public interface OfflineTaskExecAbility {
    /**
     * 执行任务
     * @param offlineTaskDTO
     * @return
     */
    Response<TaskExecResult> executeTask(OfflineTaskDTO offlineTaskDTO);
}
