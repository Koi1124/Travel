package com.han.travel.controller;

import com.han.travel.configuration.SessionConfig;
import com.han.travel.service.CollectService;
import com.han.travel.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class CollectController
{
    @Autowired
    private CollectService collectService;
    @Autowired
    private MessageService messageService;


    /**
     *@discription: 收藏 map->
     * userId: 用户id
     * collectId: 收藏对象id
     * rUserId: 收到消息用户id
     * title: 收藏对象title
     * type: 收藏对象类型
     *@param map
	 *@param session 
     *@date: 2019/7/18 10:55
     *@return: boolean
     *@author: Han
     */
    @RequestMapping("/collect")
    @ResponseBody
    public boolean collect(@RequestBody Map<String,Object> map, HttpSession session)
    {
        messageService.collect(session.getAttribute(SessionConfig.USER_NAME), String.valueOf(map.get("type")), map.get("title"), map.get("rUserId"), map.get("collectId"));
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


    /**
     *@discription: map->
     * collectId: 收藏流水号id
     *@param map 
     *@date: 2019/7/20 12:23
     *@return: boolean
     *@author: Han
     */
    @RequestMapping("/deleteCollect")
    @ResponseBody
    public boolean dCo(@RequestBody Map<String,Object> map)
    {
        return collectService.dCByCId((Integer)map.get("collectId"));
    }

}
