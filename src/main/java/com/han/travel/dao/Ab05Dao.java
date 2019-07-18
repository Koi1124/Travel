package com.han.travel.dao;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface Ab05Dao extends CheckDao
{
	Map<String,Object> queryById(int id);

    boolean changeStateById(@Param("id") int id,@Param("state") int state);

    boolean deleteById(int id);

    boolean insertCompany(@Param("dto") Map<String,Object> dto);

    Integer getAuthorIdById(int id);

    String getIntroById(int id);

    List<Map<String,Object>> getAll(Map<String,Integer> map);

    Integer selectCount();

    
    /**
     *@discription: map->
     * id: 结伴id
     * intro: 结伴简介
     * date: 结伴日期
     * authorName: 作者名
     * authorPic: 作者头像
     * star: 收藏数
     * name: 结伴目的地合集 用'|'分隔目的地
     * mddPic: 目的地图片
     *@param pid
	 *@param order
	 *@param page
	 *@param offset 
     *@date: 2019/7/17 18:26
     *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@author: Han
     */
    List<Map<String,Object>> getSearchCompInfoByMDD(@Param("pid") int pid,@Param("order") String order,@Param("page") int page,@Param("offset") int offset);

    Integer getCompTotalCountByMDD(int pid);

    boolean updateViewByComp(@Param("cid") int cid,@Param("view") int view);

    /**
     *@discription: map->
     *  id: 结伴id
     *  name: 结伴目的地名合集 用'|'分隔目的地
     *  star: 收藏数
     *  authorName: 作者名
     *  authorPic: 作者头像
     *  mddPic: 目的地图片
     *@param cid 
     *@date: 2019/7/17 16:15
     *@return: java.util.Map<java.lang.String,java.lang.Object>
     *@author: Han
     */
    Map<String,Object> getOriginDataByCid(int cid);

}
