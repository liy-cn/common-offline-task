package org.storm.commons.offlinetask.api;

import org.storm.commons.offlinetask.common.Page;
import org.storm.commons.offlinetask.domain.QueryOfflineTaskParam;
import org.storm.commons.offlinetask.domain.OfflineTaskDTO;

public interface OfflineTaskManageService {

    /**
     * 删除
     * @param id
     * @return
     */
    Boolean delete(Long id);

    /**
     * 添加
     * @param record
     * @return
     */
    Boolean insert(OfflineTaskDTO record);

    /**
     * 查询详情
     * @param id
     * @return
     */
    OfflineTaskDTO selectById(Long id);

    /**
     * 查询任务
     * @param queryOfflineTaskParam
     * @return
     */
    Page<OfflineTaskDTO> queryTask(QueryOfflineTaskParam queryOfflineTaskParam);

    /**
     * 修改状态
     * @param id
     * @param status
     * @return
     */
    Boolean updateStatus(Long id, Integer status);

    /**
     * 根据taskNo查询任务详情
     * @param taskNo
     * @return
     */
    OfflineTaskDTO selectByTaskNo(String taskNo);
}
