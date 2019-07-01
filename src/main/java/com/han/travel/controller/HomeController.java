package com.han.travel.controller;

import com.han.travel.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController
{
    @Autowired
    EmpService empService;



    @RequestMapping("/")
    public String welcomeHome()
    {
        return "home";
    }

    @RequestMapping("/home")
    public String test(Map<String,Object> map) {
        Map<String,Object> dto= new HashMap<>();
        dto.put("aab101","1");
        dto.put("aab102","尹一寒改");
        dto.put("aab107","火星");
        map.put("msg","fail");
        map.put("info",empService.query());
        if (empService.updateEmp(dto)) {
            map.put("msg","success");
        }
        return "home";
    }



}
