package com.han.travel.controller;

import com.han.travel.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class FollowController
{
    @Autowired
    private FollowService followService;


    @RequestMapping("/follow")
    @ResponseBody
    public boolean follow(@RequestBody Map<String,Object>map)
    {
        return followService.addFollow(Integer.parseInt((String) map.get("userId")),Integer.parseInt((String) map.get("followerId")));
    }

    @RequestMapping("/unfollow")
    @ResponseBody
    public boolean unfollow(@RequestBody Map<String,Object>map)
    {
        return followService.removeFollow(Integer.parseInt((String) map.get("userId")),Integer.parseInt((String) map.get("followerId")));
    }

}
