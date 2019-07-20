package com.han.travel.controller;

import com.han.travel.configuration.SessionConfig;
import com.han.travel.service.FollowService;
import com.han.travel.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class FollowController
{
    @Autowired
    private FollowService followService;
    @Autowired
    private MessageService messageService;


    /**
     *@discription: 关注用户 map->
     * userId: 被关注用户id
     * followerId: 粉丝id
     *@param map
     *@date: 2019/7/19 8:42
     *@return: boolean
     *@author: Han
     */
    @RequestMapping("/follow")
    @ResponseBody
    public boolean follow(@RequestBody Map<String,Object>map, HttpSession session)
    {
        messageService.follow(session.getAttribute(SessionConfig.USER_NAME),map.get("userId"));
        return followService.addFollow((Integer) map.get("userId"),(Integer) map.get("followerId"));
    }

    @RequestMapping("/unfollow")
    @ResponseBody
    public boolean unfollow(@RequestBody Map<String,Object>map)
    {
        return followService.removeFollow((Integer) map.get("userId"),(Integer) map.get("followerId"));
    }

}
