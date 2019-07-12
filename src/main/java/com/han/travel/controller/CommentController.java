package com.han.travel.controller;

import com.han.travel.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CommentController
 * @Description TODO
 * @Author Saki
 * @Date 2019/7/11
 * @LastUpdate 2019/7/11
 **/
@Controller
public class CommentController
{
    @Autowired
    private CommentService commentService;


    /**
     * @Author Saki
     * @Description 查钊评论及回复
     * @Date 2019/7/11
     * @param id
     * @param map
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @RequestMapping("/{id}/comment")
    @ResponseBody
    public List<Map<String, Object>> getComment(@PathVariable("id") int id, @RequestBody Map<String, Object> map)
    {
        map.put("id", id);
        return commentService.getCommentAndReplyByLatest(map);
    }

    
    /**
     * @Author Saki
     * @Description 添加评论
     * @Date 2019/7/11 
     * @param map
     * @return boolean 
     **/
    @RequestMapping("/addComment")
    @ResponseBody
    public boolean addComment(@RequestBody Map<String, Object> map)
    {
        return commentService.addComment(map);
    }

    /**
     * @Author Saki
     * @Description 添加回复
     * @Date 2019/7/11
     * @param map
     * @return boolean
     **/
    @RequestMapping("/addReply")
    @ResponseBody
    public boolean addReply(@RequestBody Map<String, Object> map)
    {
        return commentService.addReply(map);
    }
}
