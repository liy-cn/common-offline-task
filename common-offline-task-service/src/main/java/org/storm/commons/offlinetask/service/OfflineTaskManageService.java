package org.storm.commons.offlinetask.service;

import org.storm.commons.offlinetask.common.Page;
import org.storm.commons.offlinetask.dao.entity.OfflineTaskEntity;
import org.storm.commons.offlinetask.dao.entity.OfflineTaskParam;

public interface OfflineTaskManageService {
    Boolean delete(Long id);
    Boolean insert(OfflineTaskEntity record);
    OfflineTaskEntity selectById(Long id);
    Page<OfflineTaskEntity> selectTaskPage(OfflineTaskParam offlineTaskParam);
    Boolean updateStatus(Long id, Integer status);
    OfflineTaskEntity selectByTaskNo(String taskNo);
}
