package com.han.travel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Ab03Dao extends CheckDao 
{	
	List<Map<String,Object>>getAll(Map<String,Integer>map);
	
	Map<String,Object>queryById(@Param("id") int id);
	
	boolean insertAttraction(Map<String,Object>map);
	
	boolean delAttraction(@Param("id") int id );
	
	boolean updateAttraction(Map<String,Object>map);
	
	Integer selectCount();
	
	Integer fuzzySelectCount(@Param("map") Map<String,Object>map);
	
	List<Map<String,Object>> fuzzyGet(@Param("map") Map<String,Object>map);


	Map<String, Object> getSightDetailById(@Param("sid") Integer sid, @Param("uid") Integer uid);

	Map<String, Object> getSightIntroById(@Param("sid") Integer sid);

	
	/**
	 *@discription: 根据用户id得到用户收藏的景点信息 map->
     * collectId: 收藏id
     * pic: 景点图片
     * name: 景点名
     * id: 景点id
	 *@param uid 
	 *@date: 2019/7/21 20:13
	 *@return: java.util.Map<java.lang.String,java.lang.Object>
	 *@author: Han
	 */
	 List<Map<String,Object>> getCollectSightInfoByUId(int uid);
    
	 
	 /**
	  *@discription: 根据关键字模糊搜索景点 map->
      * sid: 景点id
      * sname: 景点名
      * spic: 景点图片
      * cid: 城市id
      * cname: 城市名
      * commentCount: 评论数
      * starCount: 收藏数
	  *@param keyword 
	  *@date: 2019/7/22 11:48
	  *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	  *@author: Han
	  */
	 List<Map<String,Object>> getSightsByKeyword(String keyword);
}
