package org.storm.commons.offlinetask.controllers;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.storm.commons.offlinetask.MyApi;
import org.storm.commons.offlinetask.api.OfflineTaskManageApi;
import org.storm.commons.offlinetask.api.OfflineTaskScanApi;
import org.storm.commons.offlinetask.domain.OfflineTaskDTO;

import javax.annotation.Resource;

@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoControllers {

    @Autowired
    private MyApi myApi;

    @Autowired
    private OfflineTaskManageApi offlineTaskManageApi;

    @Autowired
    private OfflineTaskScanApi offlineTaskScanApi;

    /**
     * http://localhost:8080/demo/getMyApiName
     * @return myApiImpl
     */
    @GetMapping("/getMyApiName")
    String getMyApiName() {
        String myApiName = myApi.getName();
        log.info(myApiName);
        if(offlineTaskManageApi != null){
            OfflineTaskDTO offlineTaskDTO = offlineTaskManageApi.selectById(1L);
            log.info("offlineTaskDTO:{}", JSON.toJSONString(offlineTaskDTO));
        }
        if(offlineTaskScanApi != null){
            log.info("offlineTaskDTO:{}", JSON.toJSONString(offlineTaskScanApi));
        }

        return myApiName;
    }
}
