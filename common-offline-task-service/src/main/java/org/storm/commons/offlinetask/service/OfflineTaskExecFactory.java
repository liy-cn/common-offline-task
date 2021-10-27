package org.storm.commons.offlinetask.service;

import org.storm.commons.offlinetask.ability.OfflineTaskExecAbility;
import org.storm.commons.offlinetask.exception.BusinessException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OfflineTaskExecFactory {
    private final static Map<Integer, OfflineTaskExecAbility> offlineTask = new ConcurrentHashMap<>();

    public static OfflineTaskExecAbility getTaskType(Integer taskType) {
        return offlineTask.get(taskType);
    }

    public static void register(Integer taskType, OfflineTaskExecAbility offlineTaskExecService) {
        if (taskType == null) {
            throw new BusinessException("taskType 不能为空！");
        }
        OfflineTaskExecFactory.offlineTask.put(taskType, offlineTaskExecService);
    }
}
