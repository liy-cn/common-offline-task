package org.storm.commons.commonofflinetaskweb.service.impl;

import org.springframework.stereotype.Service;
import org.storm.commons.offlinetask.MyApi;

@Service
public class MyApiImpl implements MyApi {
    @Override
    public String getName() {
        return "myApiImpl";
    }
}
