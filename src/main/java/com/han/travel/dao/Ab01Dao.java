package com.han.travel.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface Ab01Dao extends CheckDao
{
    Map<String,Object> queryById(int id);

    boolean changeStateById(@Param("id") int id,@Param("state") int state);

    boolean deleteById(int id);

    List<Map<String,Object>> getAll(Map<String,Integer> map);

    Integer selectCount();

}
