package com.han.travel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CheckDao 
{
	List<Map<String,Object>> getAll(Map<String,Integer> map);
    
    Integer selectCount();
    
    boolean changeStateById(@Param("id") int id,@Param("state") int state);
    
    List<Map<String,Object>> fuzzyGet(Map<String,Object>map);
    
    Integer fuzzySelectCount(Map<String,Object>map);
    
}
