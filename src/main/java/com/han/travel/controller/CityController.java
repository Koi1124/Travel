package com.han.travel.controller;

import com.han.travel.service.CityService;
import com.han.travel.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName CityController
 * @Description TODO
 * @Author Saki
 * @Date 2019/7/21
 * @LastUpdate 2019/7/21
 **/
@Controller
public class CityController
{
    @Autowired
    private CityService cityService;
    @Autowired
    private StrategyService strategyService;

    /**
     * @Author Saki
     * @Description 目的地页面
     * @Date 2019/7/21
     * @param dto
     * @return java.lang.String
     **/
    @RequestMapping("/mdd")
    public String mddHome(Map<String, Object> dto)
    {
        dto.put("mdds", cityService.getAllProvinceAndCity());
        return "poi/mdd";
    }

    /**
     * @Author Saki
     * @Description 城市页面
     * @Date 2019/7/21
     * @param dto
     * @return java.lang.String
     **/
    @RequestMapping("/c/{id}")
    public String poi(Map<String, Object> dto, @PathVariable("id") int id)
    {
        dto.put("routes",strategyService.getTopRouteByCidAndLimitOneRoute(id, 2));
        dto.put("id", id);
        dto.put("name", cityService.getNameById(id));
        return "poi/city";
    }

    /**
     * @Author Saki
     * @Description 根据名称搜索城市
     * @Date 2019/7/25
     * @param map
     * @return java.lang.Integer
     **/
    @PostMapping("/mdd/search")
    @ResponseBody
    public Integer getCityByName(@RequestBody Map<String, Object> map)
    {
        return cityService.getIdByName(map);
    }
}
