package com.wangyuelin.Task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 水果爬虫的定时爬取
 */
@Component("taskJob")
public class CrawerTask {
    @Scheduled(cron = "0 0/2 * * * ?")
    public void crawerFruitInfo(){
        System.out.println("开始爬取水果信息");
    }
}
