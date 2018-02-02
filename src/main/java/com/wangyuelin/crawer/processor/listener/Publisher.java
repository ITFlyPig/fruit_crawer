package com.wangyuelin.crawer.processor.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    @Autowired
    ApplicationContext applicationContext;

    public void publish(String msg, int op){
        applicationContext.publishEvent(new Event(this, msg, op));

    }
}
