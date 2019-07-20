package com.han.travel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Aa04Dao extends CheckDao 
{
	boolean changeState(@Param("map") Map<String,Object> map);
	
	Integer fuzzySelectCount(@Param("map") Map<String,Object>map);
	
	List<Map<String,Object>> fuzzyGet(@Param("map") Map<String,Object>map);
	
	boolean del(@Param("map") Map<String,Object>map);
	
	boolean insert(@Param("map") Map<String,Object>map);
	
	boolean changeRole(@Param("map") Map<String,Object>map);
	
	Map<String,Object> getPwdAndRoleByName(String name);
	
	boolean updateAdminPwd(@Param("map") Map<String,Object>map);
	
	boolean updateInfo(@Param("map") Map<String,Object>map);
	
	String getEmailByName(@Param("adminname") String adminname);
	
	Integer hasOne(@Param("adminname") String adminname);
}
