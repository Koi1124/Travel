package com.han.travel.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName Ac09Dao
 * @Description 路线
 * @Author Saki
 * @Date 2019/7/22
 **/
@Repository
public interface Ac09Dao {

    List<Map<String, Object>> getRoutesBySid(@Param("sid") int sid);

    boolean addRoute(Map<String, Object> map);

    boolean deleteRoutesBySid(@Param("sid") int sid);

}
