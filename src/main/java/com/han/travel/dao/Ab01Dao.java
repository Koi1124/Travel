package com.han.travel.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface Ab01Dao extends CheckDao
{
    Map<String,Object> queryById(int id);

    boolean changeStateById(@Param("id") int id,@Param("state") int state);

    boolean deleteById(int id);

    List<Map<String,Object>> getAll(Map<String,Integer> map);

    Integer selectCount();

    Integer addNote(@Param("map") Map<String, Object> map);

    boolean updateNote(Map<String, Object> map);

    /**
     *@discription:
     * order->排序
     * page,offset->分页
     * mdd->目的地条件查询
     *@param order
     *@param page
     *@param offset
     *@param mdd
     *@date: 2019/7/16 14:08
     *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@author: Han
     */
    List<Map<String,Object>> getHomeNotes(@Param("order")String order,@Param("page") int page,@Param("offset") int offset,@Param("mdd")String mdd);

    Integer getNotesCount(@Param("mdd") String mdd);

}
