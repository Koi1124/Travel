package com.han.travel.controller;

import com.han.travel.configuration.SessionConfig;
import com.han.travel.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class TESTController
{

    @Autowired
    MessageService messageService;

    @RequestMapping("/test")
    public String toTest(HttpSession session, Map<String,Object> map)
    {
        session.setAttribute("userId",1);


        session.setAttribute("num",messageService.getNumByUId((Integer) session.getAttribute("userId")));

        map.put("num",session.getAttribute("num"));

        return "test/test";
    }



    @RequestMapping("/tttest")
    public String toStr()
    {
        //messageService.collect("wdnmd","1","我是你爸爸",1);
        return "test/new";
    }


    @RequestMapping("/jquery")
    public String to()
    {
        return "test/click";
    }


    @RequestMapping("/userTest1")
    public String te(HttpSession session)
    {
        session.setAttribute(SessionConfig.USER_ID,1);
        session.setAttribute(SessionConfig.USER_NAME,"user1");
        return "/homepage";
    }

    @RequestMapping("/userTest2")
    public String te2(HttpSession session)
    {
        session.setAttribute(SessionConfig.USER_ID,2);
        session.setAttribute(SessionConfig.USER_NAME,"user2");
        return "/homepage";
    }

    @RequestMapping("/userTest3")
    public String te3(HttpSession session)
    {
        session.setAttribute(SessionConfig.USER_ID,3);
        session.setAttribute(SessionConfig.USER_NAME,"user3");
        return "/homepage";
    }
}
