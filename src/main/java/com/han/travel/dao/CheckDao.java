package com.han.travel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CheckDao 
{
	List<Map<String,Object>> getAll(Map<String,Integer> map);
    
    Integer selectCount();
    
    boolean changeStateById(@Param("id") int id,@Param("state") int state);
    
    List<Map<String,Object>> fuzzyGet(@Param("name") String name,@Param("intro") String intro,@Param("begin") int begin,@Param("num") int num);
    
    Integer fuzzySelectCount(@Param("name") String name,@Param("intro") String intro);
    
}
