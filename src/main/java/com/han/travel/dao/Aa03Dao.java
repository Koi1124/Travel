package com.han.travel.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface Aa03Dao
{
    String getPicById(int id);

    Integer getIdByName(String name);

    String getNameById(int id);

    Map<String,String> getPicAndNameById(int id);

    List<Integer> getProvinceId();

    List<Integer> getCitiesIdByProvince(int id);

    List<Map<String,Object>> getNamesAndPicsByComp(int cid);

    List<Map<String,Object>> getAllKidNamesAndId();

    List<Map<String, Object>> getAllNameAndId();

    List<Map<String, Object>> getAllProvince();

    List<Map<String, Object>> getCityByProvinceId(@Param("pid") int pid);
}
