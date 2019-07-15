package com.han.travel.controller;

import com.han.travel.service.ThumbsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @PostMapping("/thumbsUp")
    @ResponseBody
    public boolean thumbsUp(@RequestBody Map<String, Object> map)
    {
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
