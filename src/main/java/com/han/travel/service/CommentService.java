package com.han.travel.service;

import com.han.travel.dao.Ac00Dao;
import com.han.travel.dao.Ad00Dao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CommentService
 * @Description 评论和回复
 * @Author Saki
 * @Date 2019/7/11
 * @LastUpdate 2019/7/11
 **/
@Service
public class CommentService {

    @Resource
    private Ac00Dao ac00Dao;

    @Resource
    private Ad00Dao ad00Dao;

    /**
     * @Author Saki
     * @Description 添加评论，参数map详见DAO
     * @Date 2019/7/11
     * @param map
     * @return boolean
     **/
    public Integer addComment(Map<String, Object> map)
    {
        map.put("cid", null);
        ac00Dao.addComment(map);
        return (Integer) map.get("cid");
    }

    /**
     * @Author Saki
     * @Description 查询评论及回复
     * 参数  map {
     *          type:评论对象类型（游记、攻略、结伴）
     *          id:评论对象id
     *          order:排列顺序
     *          start:开始的位置 Limit start，pageCount
     *          pageCount:查询个数
     *      }
     * @Date 2019/7/11
     * @param map
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    private List<Map<String, Object>> getCommentAndReply(Map<String, Object> map)
    {
        List<Map<String, Object>> info = ac00Dao.getCommentByTypeAndIdOrderByLimit(map);
        for (Map<String, Object> m : info)
        {
            int parentId= (int)m.get("remarkId");
            List<Map<String, String>> reply = ad00Dao.getCompReplyByParentIdOrderBy(parentId);
            m.put("reply",reply);
        }
        return info;
    }

    /**
     * @Author Saki
     * @Description 根据最新顺序排列
     * @Date 2019/7/11
     * @param map
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    public List<Map<String,Object>> getCommentAndReplyByLatest(Map<String, Object> map)
    {
        map.put("order", " time DESC");
        return getCommentAndReply(map);
    }

    /**
     * @Author Saki
     * @Description 根据点赞顺序排列
     * @Date 2019/7/11
     * @param map
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    public List<Map<String,Object>> getCommentAndReplyByFocus(Map<String, Object> map)
    {
        map.put("order", " thumbsUpCount DESC");
        return getCommentAndReply(map);
    }


    /**
     * @Author Saki
     * @Description 根据评论id删除评论(隐藏评论)
     * @Date 2019/7/12
     * @param id
     * @return boolean
     **/
    public boolean deleteCommentById(int id)
    {
        return ac00Dao.deleteCommentById(id);
    }

    /**
     * @Author Saki
     * @Description 添加回复，参数map详见DAO
     * @Date 2019/7/11
     * @param map
     * @return boolean
     **/
    public Integer addReply(Map<String, Object> map)
    {
        map.put("rid", null);
        ad00Dao.addReply(map);
        return (Integer) map.get("rid");
    }


    /**
     * @Author Saki
     * @Description 根据id删除回复
     * @Date 2019/7/12
     * @param id
     * @return boolean
     **/
    public boolean deleteReplyById(int id)
    {
        return ad00Dao.deleteReplyById(id);
    }
}
