package com.han.travel.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface Ae01Dao
{
    List<Map<String,Object>> getLatestLetterByClient(int id);

    Integer insertLetterToUId(@Param("dto") Map<String,Object> dto);

    List<Map<String,Object>> getLettersByClinetIdAndToClientId(@Param("cid")int cid,@Param("tocid")int tocid);

    List<Integer> getClientAndToClientByLId(int id);

    boolean readDone(int id);
}