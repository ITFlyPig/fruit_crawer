package com.wangyuelin.crawer.processor;

import org.apache.log4j.BasicConfigurator;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 * 爬取的入口
 */
public class Crawer {

    public static void start(){
        Spider.create(new MonthFruitProcessor()).addPipeline(new ConsolePipeline()).addUrl(MonthFruitProcessor.url).thread(4).run();
//        Spider.create(new FruitFuncProcessor()).addPipeline(new ConsolePipeline()).addUrl(FruitFuncProcessor.url).thread(4).run();
//        Spider.create(new CookbookProcessor()).addPipeline(new ConsolePipeline()).addUrl(CookbookProcessor.getUrl("芒果")).thread(4).run();

    }


}
