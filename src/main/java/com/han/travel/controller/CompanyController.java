package com.han.travel.controller;

import com.han.travel.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class CompanyController
{
    @Autowired
    CompanyService companyService;


    @RequestMapping("/test")
    public String Test(Map<String,Object> dto)
    {
        List<Map<String,Object>> res=companyService.getTopMDD();
        System.out.println(res);
        List<Map<String,Object>> test=companyService.seachCompanyByMDD(11);
        System.out.println(test);
        dto.put("res",res);
        return "home";
    }
}
