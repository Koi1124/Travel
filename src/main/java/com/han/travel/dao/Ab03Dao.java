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
	
	Integer fuzzySelectCount(@Param("name") String name,@Param("intro") String intro);
	
	List<Map<String,Object>> fuzzyGet(@Param("name") String name,@Param("intro") String intro,@Param("begin") int begin,@Param("num") int num);
}
