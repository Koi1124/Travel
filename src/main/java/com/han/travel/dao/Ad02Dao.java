package com.han.travel.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @ClassName Ad02Dao
 * @Description TODO
 * @Author Saki
 * @Date 2019/7/12
 * @LastUpdate 2019/7/12
 **/
@Repository
public interface Ad02Dao {

    boolean thumbsUp(Map<String, Object> map);

    boolean thumbsDown(Map<String, Object> map);

}
