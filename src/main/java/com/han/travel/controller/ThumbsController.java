package com.han.travel.controller;

import com.han.travel.configuration.SessionConfig;
import com.han.travel.service.MessageService;
import com.han.travel.service.ThumbsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ClassName ThumbsController
 * @Description 点赞
 * @Author Saki
 * @Date 2019/7/12
 * @LastUpdate 2019/7/12
 **/
@Controller
public class ThumbsController
{
    @Autowired
    private ThumbsService thumbsService;
    @Autowired
    private MessageService messageService;


    /**
     * @Author Saki
     * @Description
     * @Date 2019/7/12
     * @param map {
     *            uid:用户id,
     *            type:点赞对象类型,
     *            pid:点赞对象id ->pid=3时为点赞评论
     *            rUserId: 收到消息用户id
     *            jump_type: 跳转对象类型
     *            title: 点赞内容
     *            jump_pid: 跳转对象id
     *        }
     * @return boolean
     **/
    @PostMapping("/thumbsUp")
    @ResponseBody
    public boolean thumbsUp(@RequestBody Map<String, Object> map, HttpSession session)
    {
        if ((map.get("type")).equals("3"))
        {
            messageService.thumbsUpComment(session.getAttribute(SessionConfig.USER_NAME),String.valueOf(map.get("jump_type")),map.get("title"),map.get("rUserId"),map.get("jump_pid"));
        }
        else if (map.get("rUserId") != null)
        {
            messageService.thumbsUp(session.getAttribute(SessionConfig.USER_NAME),String.valueOf(map.get("type")),map.get("title"),map.get("rUserId"),map.get("jump_pid"));
        }

        return thumbsService.thumbsUp(map);
    }

    /**
     * @Author Saki
     * @Description
     * @Date 2019/7/12
     * @param map {
     *            uid:用户id,
     *            type:点赞对象类型,
     *            pid:点赞对象id
     *        }
     * @return boolean
     **/
    @PostMapping("/thumbsDown")
    @ResponseBody
    public boolean thumbsDown(@RequestBody Map<String, Object> map)
    {
        return thumbsService.thumbsDown(map);
    }
}
