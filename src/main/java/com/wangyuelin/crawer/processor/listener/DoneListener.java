package com.wangyuelin.crawer.processor.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DoneListener implements ApplicationListener<Event> {

    public void onApplicationEvent(Event event) {

    }
}
