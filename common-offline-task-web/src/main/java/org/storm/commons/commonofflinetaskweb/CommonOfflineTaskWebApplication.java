package org.storm.commons.commonofflinetaskweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.storm.commons.offlinetask.MyApi;

import javax.annotation.Resource;

@SpringBootApplication
public class CommonOfflineTaskWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonOfflineTaskWebApplication.class, args);
    }

}
