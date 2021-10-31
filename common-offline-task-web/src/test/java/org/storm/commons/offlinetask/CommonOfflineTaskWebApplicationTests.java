package org.storm.commons.offlinetask;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j

class CommonOfflineTaskWebApplicationTests {

    @Autowired
    private MyApi myApi;

    @Test
    void contextLoads() {
      log.info("testaa");
        String myApiName = myApi.getName();
        Assertions.assertEquals("myApiImpl", myApiName);

    }

}
