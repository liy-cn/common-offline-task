package org.storm.commons.offlinetask.dao.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.storm.commons.offlinetask.dao.entity.OfflineTaskEntity;
import org.storm.commons.offlinetask.dao.entity.OfflineTaskParam;

import java.util.List;

@Mapper
public interface OfflineTaskEntityMapper {
    /**
     * 删除
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 添加
     * @param record
     * @return
     */
    int insert(OfflineTaskEntity record);

    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    OfflineTaskEntity selectById(Long id);

    /**
     * 更新状态
     * @param id
     * @param taskStatus
     * @return
     */
    int updateStatus(@Param("id") Long id, @Param("taskStatus") Integer taskStatus);

    /**
     * 更新下载url
     * @param id
     * @param taskFileAddress
     * @return
     */
    int updateAddress(@Param("id") Long id, @Param("taskFileAddress") String taskFileAddress);

    /**
     * 更新错误信息
     * @param id
     * @param taskResultMsg
     * @return
     */
    int updateResultMsg(@Param("id") Long id, @Param("taskResultMsg") String taskResultMsg);

    /**
     * 分页查询任务列表
     * @param param
     * @return
     */
    List<OfflineTaskEntity> selectTaskPage(OfflineTaskParam param);

    /**
     * 查询任务总数
     * @param param
     * @return
     */
    int selectTaskCount(OfflineTaskParam param);

    /**
     * 通过taskNo查询任务信息
     * @param taskNo
     * @return
     */
    OfflineTaskEntity selectByTaskNo(String taskNo);

}
