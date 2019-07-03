package com.han.travel.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface Ac07Dao
{
    boolean insertMDD(@Param("dto")Map<String,Object> dto);

    int placeCount(int pid);

    boolean deleteById(int id);

    List<Integer> getAllMDD();

    List<Integer> getCompByMDD(int pid);

    List<Integer> getMDDByComp(int cid);

}
