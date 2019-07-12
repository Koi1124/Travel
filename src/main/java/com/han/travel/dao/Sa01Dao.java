package com.han.travel.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface Sa01Dao
{
    boolean insertMessage(@Param("userId") int userId,@Param("content")String content);

    boolean deleteMessage(int id);

    Integer getCount(int uid);

    List<String> messageByUId(int uId);


}
