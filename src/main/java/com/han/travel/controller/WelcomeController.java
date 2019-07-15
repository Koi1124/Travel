package com.han.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName WelcomeController
 * @Description 基本跳转页面
 * @Author Saki
 * @Date 2019/7/13
 * @LastUpdate 2019/7/13
 **/
@Controller
public class WelcomeController
{

    @RequestMapping("/")
    public String userLogin()
    {
        return "login";
    }

    @RequestMapping("/agency")
    public String agencyLogin()
    {
        return "login";
    }

}
