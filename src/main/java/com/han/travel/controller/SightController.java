package com.han.travel.controller;

import com.han.travel.service.SightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SightController
 * @Description TODO
 * @Author Saki
 * @Date 2019/7/22
 * @LastUpdate 2019/7/22
 **/
@Controller
public class SightController
{
    @Autowired
    private SightService sightService;

    /**
     * @Author Saki
     * @Description 获取所有的景点id和名称
     * {
     *     id:
     *     label:
     *     value:
     * }
     * @Date 2019/7/22
     * @param
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @RequestMapping("/getAllSights")
    @ResponseBody
    public List<Map<String, Object>> getAllSight()
    {
        return sightService.getAllSightsIdAndName();
    }
}
