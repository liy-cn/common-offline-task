package org.storm.commons.offlinetask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.storm"})
public class CommonOfflineTaskWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonOfflineTaskWebApplication.class, args);
    }

}
