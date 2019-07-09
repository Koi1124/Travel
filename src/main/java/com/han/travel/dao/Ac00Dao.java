package com.han.travel.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface Ac00Dao
{
    boolean commentComp(@Param("dto")Map<String,Object> dto);

    List<Map<String,Object>> getParentCompCommentByCIdOrderBy(@Param("cid") int cid, @Param("order")String order);

    List<Map<String,Object>> getKidCompCommentByParentIdOrderBy(@Param("pid") int pid);

}
