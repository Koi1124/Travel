package com.han.travel.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface Sa01Dao
{
    boolean insertMessage(@Param("dto") Map<String,Object> dto);

    boolean deleteMessage(int id);

    Integer getCount(int uid);
    
    /**
     *@discription: map->
     * msg: 消息内容
     * type: 消息类型
     * mid: 消息id
     * pid: 消息跳转对象id
     *@param uId 
     *@date: 2019/7/16 18:59
     *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@author: Han
     */
    List<Map<String,Object>> messageByUId(int uId);

    boolean clearAllByUId(int uId);
}
