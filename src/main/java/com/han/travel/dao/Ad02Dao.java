package com.han.travel.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @ClassName Ad02Dao
 * @Description 点赞
 * @Author Saki
 * @Date 2019/7/12
 * @LastUpdate 2019/7/12
 **/
@Repository
public interface Ad02Dao {

    boolean thumbsUp(Map<String, Object> map);

    boolean thumbsDown(Map<String, Object> map);

    Integer isThumbsUp(@Param("uid") Integer uid, @Param("type") Integer type,
                       @Param("pid") Integer pid);

    Integer getThumbsCountByTypeAndId(@Param("type") int type, @Param("id") int id);

}
