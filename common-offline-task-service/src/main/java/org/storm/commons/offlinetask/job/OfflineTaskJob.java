package org.storm.commons.offlinetask.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.storm.commons.offlinetask.service.OfflineTaskScanService;

import javax.annotation.Resource;

@Slf4j
@Component
@Configuration
@EnableScheduling
public class OfflineTaskJob {
    @Resource
    private OfflineTaskScanService offlineTaskScanService;

    @Scheduled(cron = "*/5 * * * * ?")
    public void doScan() {
        log.info("OfflineTaskJob start");
        long start=System.currentTimeMillis();
        offlineTaskScanService.scanTask();
        long end =System.currentTimeMillis();
        log.info("OfflineTaskJob end. time:{}ms\n",end-start);

    }
}
