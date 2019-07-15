package com.han.travel.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName Ad00Dao
 * @Description 回复
 * @Author Saki
 * @Date 2019/7/11
 * @LastUpdate 2019/7/11
 **/
@Repository
public interface Ad00Dao {

    /**
     * @Author Saki
     * @Description 根据评论id获取其下的回复
     *  返回 map {
     *          replierId：用户id
     *          replierName：用户昵称
     *          replierPic：用户头像
     *          content：内容
     *          time：时间
     *          respondedId：被回复者id
     *          respondedName：被回复者昵称
     *      }
     * @Date 2019/7/11
     * @param pid
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     **/
    List<Map<String,String>> getCompReplyByParentIdOrderBy(@Param("pid") int pid);

    /**
     * @Author Saki
     * @Description 回复单一实例插入
     *   参数 map {
     *          userId：用户id
     *          commentId：父级评论id
     *          replyId：被回复者id
     *          content：回复内容
     *       }
     * @Date 2019/7/11
     * @param map
     * @return boolean
     **/
    boolean addReply(@Param("map") Map<String, Object> map);

    /**
     * @Author Saki
     * @Description 根据回复id来删除回复
     * @Date 2019/7/12
     * @param id
     * @return boolean
     **/
    boolean deleteReplyById(int id);
}
