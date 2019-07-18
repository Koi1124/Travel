package com.han.travel.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface Ac05Dao
{
    boolean insertCompApp(@Param("dto")Map<String, Object> dto);

    boolean changeAppState(@Param("id") int id,@Param("state") int state);

    Integer getAppCountByCId(int cid);

    Map<String,Object> getAppCountAndPNameAndPIdByPId(int id);

    List<Map<String,Object>> getApplicantsIdAndNameAndPicByCId(int cid);

    Map<String, Object> getAppByUidAndTid(Map<String, Object> map);

    boolean updateAppByUidAndTid(Map<String, Object> map);
}
