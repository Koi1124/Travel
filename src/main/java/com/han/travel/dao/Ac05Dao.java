package com.han.travel.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface Ac05Dao
{
    boolean insertCompApp(@Param("dto")Map<String,Object> dto);

    boolean changeAppState(@Param("id") int id,@Param("state") int state);
}
