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

    @RequestMapping("/login")
    public String userLogin()
    {
        return "login";
    }

    @RequestMapping("/agency")
    public String agencyLogin()
    {
        return "login";
    }

    @RequestMapping("/logout")
    public String logOut(HttpSession session)
    {
        session.removeAttribute(SessionConfig.USER_ID);
        session.removeAttribute(SessionConfig.USER_NAME);
        session.removeAttribute(SessionConfig.USER_LOGO);
        return "redirect:";
    }

    @RequestMapping({"","/"})
    public String toHome()
    {
        return "homepage";
    }

}
