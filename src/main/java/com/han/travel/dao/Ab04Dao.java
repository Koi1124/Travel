package com.han.travel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Ab04Dao extends CheckDao 
{
	List<Map<String,Object>>getAll(Map<String,Integer>map);
	
	Integer selectCount();
	
	boolean changeStateById(@Param("id") int id,@Param("state") int state);
}
