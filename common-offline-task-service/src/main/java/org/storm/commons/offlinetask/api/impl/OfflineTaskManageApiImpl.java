package org.storm.commons.offlinetask.api.impl;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.storm.commons.offlinetask.api.OfflineTaskManageApi;
import org.storm.commons.offlinetask.common.Page;
import org.storm.commons.offlinetask.dao.entity.OfflineTaskEntity;
import org.storm.commons.offlinetask.dao.entity.OfflineTaskParam;
import org.storm.commons.offlinetask.domain.OfflineTaskDTO;
import org.storm.commons.offlinetask.domain.QueryOfflineTaskParam;
import org.storm.commons.offlinetask.service.OfflineTaskManageService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OfflineTaskManageApiImpl implements OfflineTaskManageApi {

    @Autowired
    private OfflineTaskManageService offlineTaskManageService;

    @Override
    public Boolean delete(Long id) {
        return offlineTaskManageService.delete(id);
    }

    @Override
    public Boolean insert(OfflineTaskDTO record) {
        return offlineTaskManageService.insert(this.convertToEntity(record));
    }

    private OfflineTaskEntity convertToEntity(OfflineTaskDTO record) {
        return null;
    }

    @Override
    public OfflineTaskDTO selectById(Long id) {
        return this.convertToDto(offlineTaskManageService.selectById(id));
    }


    @Override
    public Page<OfflineTaskDTO> queryTask(QueryOfflineTaskParam queryOfflineTaskParam) {
        Page<OfflineTaskEntity> offlineTaskEntityPage = offlineTaskManageService.selectTaskPage(this.convertOfflineTaskParam(queryOfflineTaskParam));
        return convertOfflineTaskPage(offlineTaskEntityPage);
    }

    private List<OfflineTaskDTO> convertToDtoList(List<OfflineTaskEntity> offlineTaskEntityList) {
        if (CollectionUtils.isEmpty(offlineTaskEntityList)) {
            return new ArrayList<>();
        }
        return offlineTaskEntityList.stream().map(entity -> {
            OfflineTaskDTO dto = new OfflineTaskDTO();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    private Page<OfflineTaskDTO> convertOfflineTaskPage(Page<OfflineTaskEntity> offlineTaskEntityPage) {
        Page<OfflineTaskDTO> target = new Page<>();
        target.setSize(offlineTaskEntityPage.getSize());
        target.setTotalElements(offlineTaskEntityPage.getTotalElements());
        target.setContent(this.convertToDtoList(offlineTaskEntityPage.getContent()));
        target.setPage(offlineTaskEntityPage.getPage());
        return target;
    }


    private OfflineTaskParam convertOfflineTaskParam(QueryOfflineTaskParam source) {
        OfflineTaskParam target = new OfflineTaskParam();
        target.setCurrentPage(source.getPageNum());
        target.setPageSize(source.getPageSize());
        target.setClientInfo(source.getClientInfo());
        target.setPageNum(source.getPageNum());
        target.setClientCode(source.getClientCode());
        target.setTaskNo(source.getTaskNo());
        target.setTaskType(source.getTaskType());
        target.setTaskStatus(source.getTaskStatus());
        target.setTaskName(source.getTaskName());
        target.setTaskDescription(source.getTaskDescription());
        return target;
    }

    @Override
    public Boolean updateStatus(Long id, Integer status) {
        return offlineTaskManageService.updateStatus(id, status);
    }

    @Override
    public OfflineTaskDTO selectByTaskNo(String taskNo) {
        return this.convertToDto(offlineTaskManageService.selectByTaskNo(taskNo));
    }

    private OfflineTaskDTO convertToDto(OfflineTaskEntity entity) {
        if (entity == null) {
            return null;
        }
        OfflineTaskDTO dto = new OfflineTaskDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }


}
