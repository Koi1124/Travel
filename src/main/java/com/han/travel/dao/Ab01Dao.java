package com.han.travel.dao;

import org.jboss.logging.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface Ab01Dao
{
    Map<String,Object> temp = new HashMap<>();

    List<Map<String,String>> query();

    boolean updateEmp(Map<String,Object> dto);

}
