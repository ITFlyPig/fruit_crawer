package com.wangyuelin.app.controller;

import com.wangyuelin.app.model.MonthBean;
import com.wangyuelin.app.model.RespBean;
import com.wangyuelin.app.service.FruitInfoService;
import com.wangyuelin.crawer.model.Fruit;
import com.wangyuelin.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/fruit")
public class FruitController {

    @Autowired
    private FruitInfoService fruitInfoService;

    List<Fruit> getAllFruitInfo(){
        return null;

    }

    @RequestMapping("/getMonth")
    public @ResponseBody RespBean<List<MonthBean>> getMonths(){
        List<MonthBean> list = fruitInfoService.getMonths();
        RespBean<List<MonthBean>> resp = new RespBean<List<MonthBean>>();
        resp.setCode(Constant.RespCode.SUCCESS);
        resp.setMsg(Constant.RespMsg.SUCCESS);
        resp.setData(list);
        return resp;
    }


}
