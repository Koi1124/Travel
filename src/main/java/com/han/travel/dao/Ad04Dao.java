package com.han.travel.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Ad04Dao
{
    Integer getStarCountByIdAndType(@Param("id") Integer id,@Param("type") Integer type);

    Integer getStarCountByMDDID(Integer id);

}
