package com.han.travel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CheckDao 
{
	List<Map<String,Object>> getAllByState(Map<String,Integer> map);
    
    Integer selectCount();
    
    boolean changeStateById(@Param("id") int id,@Param("state") int state);
}
