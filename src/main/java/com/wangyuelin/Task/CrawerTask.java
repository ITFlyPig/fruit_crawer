package com.wangyuelin.Task;

import com.wangyuelin.crawer.processor.MonthFruitProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 水果爬虫的定时爬取
 */
@Component("taskJob")
public class CrawerTask {

    @Autowired
    private MonthFruitProcessor monthFruitProcessor;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void crawerFruitInfo(){
//        monthFruitProcessor.start();

    }
}
