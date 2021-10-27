package org.storm.commons.offlinetask.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.storm.commons.offlinetask.common.Page;
import org.storm.commons.offlinetask.dao.entity.OfflineTaskEntity;
import org.storm.commons.offlinetask.dao.entity.OfflineTaskParam;
import org.storm.commons.offlinetask.dao.mapper.OfflineTaskEntityMapper;
import org.storm.commons.offlinetask.domain.OfflineTaskDTO;
import org.storm.commons.offlinetask.service.OfflineTaskManageService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OfflineTaskManageServiceImpl implements OfflineTaskManageService {

    @Autowired
    private OfflineTaskEntityMapper offlineTaskEntityMapper;

    @Override
    public Boolean delete(Long id) {
        return offlineTaskEntityMapper.delete(id) != 0;
    }

    @Override
    public Boolean insert(OfflineTaskEntity record) {
        return offlineTaskEntityMapper.insert(record) != 0;
    }

    @Override
    public OfflineTaskEntity selectById(Long id) {
        return offlineTaskEntityMapper.selectById(id);
    }

    @Override
    public Page<OfflineTaskEntity> selectTaskPage(OfflineTaskParam offlineTaskParam) {
        List<OfflineTaskEntity> offlineTaskEntities = offlineTaskEntityMapper.selectTaskPage(offlineTaskParam);
        int totalCount = offlineTaskEntityMapper.selectTaskCount(offlineTaskParam);
        return convertOfflineTaskPage(offlineTaskParam, offlineTaskEntities, totalCount);
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

    private Page<OfflineTaskEntity> convertOfflineTaskPage(OfflineTaskParam param, List<OfflineTaskEntity> source, int totalCount) {
        Page<OfflineTaskEntity> target = new Page<>();
        target.setSize(param.getPageSize());
        target.setTotalElements(totalCount);
        target.setContent(source);
        target.setPage(param.getPageNum());
        return target;
    }


    @Override
    public Boolean updateStatus(Long id, Integer status) {
        boolean result = offlineTaskEntityMapper.updateStatus(id, status) != 0;
        log.info("updateStatus()结束，入参id={}，status={}，出参result={}", id, status, result);
        return result;
    }

    @Override
    public OfflineTaskEntity selectByTaskNo(String taskNo) {
        return offlineTaskEntityMapper.selectByTaskNo(taskNo);
    }

}
