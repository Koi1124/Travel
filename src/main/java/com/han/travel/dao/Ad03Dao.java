package com.han.travel.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Repository
public interface Ad03Dao
{
    boolean addFollow(@Param("userId")Integer userId,@Param("followerId")Integer followerId);

    boolean removeFollow(@Param("userId")Integer userId,@Param("followerId")Integer followerId);

    Integer isFollow(@Param("userId")Integer userId,@Param("followerId")Integer followerId);

    List<Map<String, Object>> getFollows(@Param("uid") Integer uid);

    List<Map<String, Object>> getFans(@Param("uid") Integer uid);

    //额外获取自己有无关注该用户的关注的用户，下同
    List<Map<String, Object>> getUserFollows(@Param("uid") Integer uid, @Param("myId") Integer myId);

    List<Map<String, Object>> getUserFans(@Param("uid") Integer uid, @Param("myId") Integer myId);
}
