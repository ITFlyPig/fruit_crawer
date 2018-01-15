package com.wangyuelin.controller;

import com.wangyuelin.crawer.model.MonthFruitBean;
import com.wangyuelin.crawer.processor.Crawer;
import com.wangyuelin.crawer.processor.FruitFuncProcessor;
import com.wangyuelin.crawer.processor.MonthFruitProcessor;
import com.wangyuelin.crawer.service.FruitInfoService;
import com.wangyuelin.model.User;
import com.wangyuelin.service.UserService;
import org.apache.log4j.BasicConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/mvc")
public class TestController {
    @Autowired
    private FruitFuncProcessor fruitFuncProcessor;

    @Autowired
    private FruitInfoService fruitInfoService;

    /**
     * 在返回的数据添加@ResponseBody注解，表明是要转换为json
     * 不添加的话则表示返回的是视图资源
     *
     */


    /**
     * 返回视图
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        return "/jsp/index.jsp";
    }

    /**
     * 返回json
     * @return
     */
    @RequestMapping("/getUser")
    public @ResponseBody
    User getUser(){
        User user = new User();
        user.setId(3232);
        user.setName("王厂长");
        user.setSex(2);
        return user;
    }



    @Autowired
    private UserService userService;

    @RequestMapping("/getUserBySql")
    public @ResponseBody
    User getUserBySql(){

        BasicConfigurator.configure();
        Spider.create(fruitFuncProcessor).addPipeline(new ConsolePipeline()).addUrl(FruitFuncProcessor.url).thread(4).run();
        return userService.findById(1);
    }


    @RequestMapping("/addOne")
    public String addOne(String name){
        MonthFruitBean bean = new MonthFruitBean();
        bean.setMonthNum(12);
        bean.setMonth("12");
        bean.setFruitStr("你是傻逼吗 Navicat");
        fruitInfoService.save(bean);
//        MonthFruitBean bean = fruitInfoService.getByMonth(3);
//        System.out.println(bean + " 接收到的参数：name--" + name);
//        try {
//            String str = new String(bean.getFruitStr().getBytes("utf-8"), "utf-8");
//            System.out.println(str);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        return "index";


    }


    @RequestMapping("/findByName")
    public @ResponseBody User findByName(String name){
        return userService.findByName(name);

    }

}
