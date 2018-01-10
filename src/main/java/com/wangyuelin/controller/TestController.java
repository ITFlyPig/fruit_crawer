package com.wangyuelin.controller;

import com.wangyuelin.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mvc")
public class TestController {

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
        return "index";
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




}
