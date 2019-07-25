package com.han.travel.controller;


import com.han.travel.configuration.SessionConfig;
import com.han.travel.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

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

    @Autowired
    private NoteService noteService;

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
    public String toHome(Map<String, Object> dto)
    {
        dto.put("notes" ,noteService.getTopNotes());
        return "homepage";
    }

}
