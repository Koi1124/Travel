package com.han.travel.controller;

import com.han.travel.component.SocketServer;
import com.han.travel.service.LetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/letter")
public class LetterController
{
    @Autowired
    private SocketServer socketServer;

    @Autowired
    LetterService letterService;

    @RequestMapping("")
    public String toHomePage(Map<String,Object> dto)
    {
        dto.put("content","私信测试");
        dto.put("clientId",2);
        dto.put("toClientId",3);
        letterService.insertLettter(dto);
        List<Map<String,Object>> test=letterService.getLatestLettersByClient(2);
        System.out.println(test);
        return "letter/homepage";
    }


}
