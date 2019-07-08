package com.han.travel.controller;

import com.han.travel.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
        return companyService.getTopMDD();
    }

    @RequestMapping("/mdd_search")
    @ResponseBody
    public List<Map<String,Object>> getSearch()
    {
        System.out.println(companyService.prepareAllMDDInfo());
        return companyService.prepareAllMDDInfo();
    }

    @RequestMapping("/company_search")
    @ResponseBody
    public Map<String,Object> getCompany(@RequestBody Map<String,Integer> dto)
    {
        int id=dto.get("id");
        int page=dto.get("page");
        int itemCount=dto.get("itemCount");
        return companyService.searchCompByMDDOrderByLatest(id,(page-1)*itemCount,itemCount);
    }

    @RequestMapping("")
    public String toHomepage()
    {
        return "together/homepage";
    }


    @RequestMapping("/company/detail")
    public String toCompanyDetail(@RequestParam Map<String,Object> dto,Map<String,Object> map)
    {
        Map<String,Object> detail=companyService.getCompDetail(dto);
        for (Map.Entry m:detail.entrySet())
        {
            map.put((String) m.getKey(),m.getValue());
        }
        System.out.println(map);
        return "together/test";
    }


//    @RequestMapping("/")
//    public String test()
//    {
//        List<Map<String, Object>> maps = companyService.searchCompByMDD(17);
//        System.out.println(maps);
//        return "home";
//    }


}
