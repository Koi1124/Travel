package com.han.travel.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Repository
public interface Ab01Dao extends CheckDao
{
    /**==============================================================================
     *                              管理员操作
     * ==============================================================================
     */

    Map<String,Object> queryById(int id);

    boolean changeStateById(@Param("id") int id,@Param("state") int state);

    boolean deleteById(int id);

    List<Map<String,Object>> getAll(Map<String,Integer> map);

    Integer selectCount();

    /**==============================================================================
     *                              用户操作
     * ==============================================================================
     */
    Integer addNote(@Param("map") Map<String, Object> map);

    boolean updateNote(Map<String, Object> map);

    List<Map<String, Object>> getDraftByUid(int uid);

    Map<String, Object> getMyNoteById(int nid);

    //添加浏览量
    boolean addViews(int nid);

    Map<String, Object> getNoteByIdAndStatus(@Param("nid") int nid, @Param("status") Integer status);

    /**
     * @Author Saki
     * @Description 获得用户相关游记的信息：是否点赞、收藏、关注作者
     * @Date 2019/7/18
     * @param nid 游记id
     * @param uid 作者id
     * @param myId 用户id
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> getNoteExtraMsgByIdAndUid(@Param("nid") int nid, @Param("uid") int uid,
                                                  @Param("myId") int myId);


    Integer getNoteCountByUid(@Param("uid") int uid);

    List<Map<String, Object>> getNoteByUid(@Param("uid") int uid);

    /**==============================================================================
     *                              首页查询
     * ==============================================================================
     */

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
