package com.han.travel.controller;

import com.han.travel.configuration.SessionConfig;
import com.han.travel.service.CommentService;
import com.han.travel.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CommentController
 * @Description 评论及回复controller（增、删）
 * @Author Saki
 * @Date 2019/7/11
 * @LastUpdate 2019/7/11
 **/
@Controller
public class CommentController
{
    @Autowired
    private CommentService commentService;
    @Autowired
    private MessageService messageService;


    /**
     * @Author Saki
     * @Description 查钊评论及回复
     * @Date 2019/7/11
     * @param map {
     *          type:评论对象类型（游记、攻略、结伴）
     *          start:开始的位置 Limit start，pageCount
     *          pageCount:查询个数
     *          id:评论对象id
     *        }
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @PostMapping("/comment")
    @ResponseBody
    public List<Map<String, Object>> getComment(@RequestBody Map<String, Object> map)
    {
        return commentService.getCommentAndReplyByLatest(map);
    }


    /**
     * @Author Saki
     * @Description 添加评论
     * @Date 2019/7/11
     * @param map {
     *          userId：用户id
     *          type:评论对象类型（游记、攻略、结伴）
     *          pid:评论对象id
     *          title: 评论对象title
     *          content：评论内容
     *          score：评分（仅景点和旅游项目有，其他均为0）
     *           }
     * @return boolean
     **/
    @PostMapping("/addComment")
    @ResponseBody
    public Integer addComment(@RequestBody Map<String, Object> map, HttpSession session)
    {
        messageService.comment(session.getAttribute(SessionConfig.USER_NAME), String.valueOf(map.get("type")), map.get("title"),map.get("rUserId"),map.get("pid"));
        return commentService.addComment(map);
    }


    /**
     * @Author Saki
     * @Description 删除评论
     * @Date 2019/7/12
     * @param map {
     *            id：评论id
     *        }
     * @return boolean
     **/
    @PostMapping("/deleteComment")
    @ResponseBody
    public boolean deleteComment(@RequestBody Map<String, String> map)
    {
        return commentService.deleteCommentById(Integer.valueOf(map.get("id")));
    }

    /**
     * @Author Saki
     * @Description 添加回复
     * @Date 2019/7/11
     * @param map {
     *          userId：用户id
     *          commentId：父级评论id
     *          replyId：被回复者id
     *          content：回复内容
     *       }
     * @return boolean
     **/
    @PostMapping("/addReply")
    @ResponseBody
    public Integer addReply(@RequestBody Map<String, Object> map)
    {
        System.out.println(map);
        return commentService.addReply(map);
    }


    /**
     * @Author Saki
     * @Description 根据id删除回复
     * @Date 2019/7/12
     * @param map {
     *           id：回复id
     *        }
     * @return boolean
     **/
    @PostMapping("/deleteReply")
    @ResponseBody
    public boolean deleteReply(@RequestBody Map<String, String> map)
    {
        return commentService.deleteReplyById(Integer.valueOf(map.get("id")));
    }
}