package com.han.travel.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface Ad04Dao
{
    Integer getStarCountByIdAndType(@Param("id") Integer id,@Param("type") Integer type);

    Integer getStarCountByMDDID(Integer id);

    boolean insertCollect(@Param("map")Map<String,Object> map);

    Integer isCollect(@Param("map")Map<String,Object> map);


}
