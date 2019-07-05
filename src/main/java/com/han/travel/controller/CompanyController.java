package com.han.travel.controller;

import com.han.travel.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/together")
public class CompanyController
{
    @Autowired
    CompanyService companyService;


    /**
     *@discription: 存入dto，返回结伴主页
     *@param
     *@date: 2019/7/5 9:01
     *@return: java.lang.String
     *@author: Han
     */
    @RequestMapping("/mdd_top")
    @ResponseBody
    public List<Map<String,Object>> getTop()
    {
        //dto.put("topMDD",companyService.getTopMDD());
        //System.out.println(companyService.prepareAllMDDInfo());
        return companyService.getTopMDD();
    }

    @RequestMapping("/mdd_search")
    @ResponseBody
    public List<Map<String,Object>> getSearch()
    {
        return companyService.prepareAllMDDInfo();
    }

    @RequestMapping("")
    public String toHomepage()
    {
        return "together/homepage";
    }


//    @RequestMapping("/")
//    public String test()
//    {
//        List<Map<String, Object>> maps = companyService.searchCompByMDD(17);
//        System.out.println(maps);
//        return "home";
//    }


}
