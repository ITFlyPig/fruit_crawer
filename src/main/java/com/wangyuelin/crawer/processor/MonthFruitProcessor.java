package com.wangyuelin.crawer.processor;

import com.wangyuelin.crawer.model.MonthFruitBean;
import com.wangyuelin.crawer.processor.listener.Event;
import com.wangyuelin.crawer.processor.listener.Publisher;
import com.wangyuelin.crawer.service.FruitInfoService;
import com.wangyuelin.crawer.service.impl.FruitInfoServiceImpl;
import com.wangyuelin.service.UserService;
import com.wangyuelin.util.Constant;
import com.wangyuelin.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.xsoup.XElements;
import us.codecraft.xsoup.Xsoup;

import java.util.ArrayList;
import java.util.List;

@Component
public class MonthFruitProcessor implements PageProcessor {

    @Autowired
    Publisher publisher;

    @Autowired
    private FruitInfoService fruitInfoService;

    private int[] monthInt = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private String[] monthArray = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};


    private Site site = Site.me().setRetryTimes(3).setSleepTime(0);
    public static final String url = "https://jingyan.baidu.com/article/9113f81b042ffa2b3214c7bc.html";//抽取的网址

    public void process(Page page) {
        List<String> p = page.getHtml().css(".content-listblock-text > p").all();
        int size = p.size();

        ArrayList<MonthFruitBean> list = new ArrayList<MonthFruitBean>();
        for (int i = 0; i < size; i++){
            XElements monthElements = Xsoup.select(p.get(i), "//strong/text()");
            String month =  monthElements.get();
            XElements fruitElements = Xsoup.select(p.get(i), "//p/text()");
            String fruitStr = fruitElements.get();


            MonthFruitBean bean = new MonthFruitBean();
            bean.setMonth(month);
            String rs = fruitStr.replaceAll("\\u00A0"," ");//去除160的空格
            bean.setFruitStr(TextUtil.removeSpecialCharacter(rs, " ", " \n"));
            bean.setMonthNum(parseMonth(month));
            list.add(bean);

        }

        page.putField("monthFruit", list);
        //保存到数据库
        fruitInfoService.save(list);

        //发布爬取完成的事件
        publisher.publish("月份爬取完成", Constant.EventType.MONTH_FRUIT_DONE);


    }

    public Site getSite() {
        return site;
    }


    /**
     * 将大写的月份转为数字的月份
     * @param monthStr
     * @return
     */
    private int parseMonth(String monthStr){
        if (TextUtil.isEmpty(monthStr)){
            return 0;
        }

        int size = monthArray.length;
        for (int i = size - 1; i >= 0; i--){
            if (monthStr.contains(monthArray[i])){
                return monthInt[i];
            }
        }
        return 0;
    }


    /**
     * 开始爬取
     */
    public void start(){
        Spider.create(this).addPipeline(new ConsolePipeline()).addUrl(url).thread(3).run();

    }

}
