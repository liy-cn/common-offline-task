package org.storm.commons.commonofflinetaskweb.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.storm.commons.offlinetask.MyApi;

@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoControllers {

    @Autowired
    private MyApi myApi;

    /**
     * http://localhost:8080/demo/getMyApiName
     * @return myApiImpl
     */
    @GetMapping("/getMyApiName")
    String getMyApiName() {
        String myApiName = myApi.getName();
        log.info(myApiName);
        return myApiName;
    }
}
