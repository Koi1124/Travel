package com.han.travel.controller;

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
        messageService.collect("wdnmd","1","我是你爸爸",1);
        return "test/click";
    }

}
