package com.han.travel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Ab02Dao extends CheckDao 
{	
	List<Map<String,Object>>getAll(Map<String,Integer>map);
	
	Map<String,Object>queryById(@Param("id") int id);
	
	boolean insertStrategy(Map<String,Object>map);
	
	boolean delStrategy(@Param("id") int id );
	
	boolean updateStrategy(Map<String,Object>map);
	
	Integer selectCount();
	
	Integer fuzzySelectCount(@Param("city") String city,@Param("name") String name);
	
	List<Map<String,Object>> fuzzyGet(@Param("city") String city,@Param("name") String name,@Param("begin") int begin,@Param("num") int num);
}
