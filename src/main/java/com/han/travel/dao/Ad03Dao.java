package com.han.travel.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Ad03Dao
{
    boolean addFollow(@Param("userId")Integer userId,@Param("followerId")Integer followerId);

    boolean removeFollow(@Param("userId")Integer userId,@Param("followerId")Integer followerId);

    Integer isFollow(@Param("userId")Integer userId,@Param("followerId")Integer followerId);

}
