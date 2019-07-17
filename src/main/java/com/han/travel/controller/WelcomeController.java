package com.han.travel.controller;

import com.han.travel.configuration.SessionConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

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

    @RequestMapping("/noteTest")
    public String noteTest(HttpSession session)
    {
        session.setAttribute(SessionConfig.USER_ID, 1);
        return "note/edit";
    }

}
