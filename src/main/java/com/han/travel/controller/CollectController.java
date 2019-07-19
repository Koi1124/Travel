package com.han.travel.controller;

import com.han.travel.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class CollectController
{
    @Autowired
    private CollectService collectService;


    /**
     * @Author Saki
     * @Description 关注
     * @Date 2019/7/15
     * @param map {
     *           userId:,
     *           collectId:,
     *           type:
     *        }
     * @return boolean
     **/
    @RequestMapping("/collect")
    @ResponseBody
    public boolean collect(@RequestBody Map<String,Object> map)
    {
        return collectService.addCollect(map);
    }

    /**
     * @Author Saki
     * @Description 取消关注
     * @Date 2019/7/15
     * @param map {
     *           userId:,
     *           collectId:,
     *           type:
     *        }
     * @return boolean
     **/
    @RequestMapping("/uncollect")
    @ResponseBody
    public boolean uncollect(@RequestBody Map<String,Object> map)
    {
        return collectService.deleteCollect(map);
    }

}
