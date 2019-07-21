package com.han.travel.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface Ac00Dao
{

    /**
     * @Author Saki
     * @Description 添加评论
     *  参数 map {
     *          userId：用户id
     *          type:评论对象类型（游记、攻略、结伴）
     *          pid:评论对象id
     *          content：评论内容
     *          score：评分（仅景点和旅游项目有，其他均为0）
     *      }
     * @Date 2019/7/11
     * @param dto
     * @return boolean
     **/
    boolean addComment(@Param("map") Map<String, Object> dto);

    /**
     * @Author Saki
     * @Description 根据以下查询评论
     * 参数  map {
     *          type:评论对象类型（游记、攻略、结伴）
     *          id:评论对象id
     *          order:排列顺序
     *          start:开始的位置 Limit start，pageCount
     *          pageCount:查询个数
     *      }
     *
     *  结果 map {
     *          remarkId：评论id
     *          remarkerName：用户昵称
     *          remarkerId：用户id
     *          remarkerPic：用户头像
     *          content：评论内容
     *          thumbsUpCount：点赞数
     *          time：发布时间
     *          score：评分
     *      }
     * @Date 2019/7/11
     * @param map
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     **/
    List<Map<String, Object>> getCommentByTypeAndIdOrderByLimit(Map<String, Object> map);


    /**
     * @Author Saki
     * @Description 根据id删除评论（隐藏评论）
     * @Date 2019/7/12
     * @param id
     * @return boolean
     **/
    boolean deleteCommentById(int id);


    /**
     *@discription: 根据用户id获得评论信息 map->
     * type: 评论类型 1->游记 2->系统攻略 3->景点 5->结伴
     * time: 发表时间
     * title: 评论对象标题
     * content: 评论内容
     * toCId: 评论对象id
     *@param uid 
     *@date: 2019/7/21 0:41
     *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@author: Han
     */
    List<Map<String,Object>> getCompanyCommentInfoByUId(int uid);
    List<Map<String,Object>> getNoteCommentInfoByUId(int uid);
    List<Map<String,Object>> getSightCommentInfoByUId(int uid);
    List<Map<String,Object>> getSysCommentInfoByUId(int uid);

}
