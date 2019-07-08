package com.han.travel.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface Aa03Dao
{
    String getPicById(int id);

    String getNameById(int id);

    Map<String,String> getPicAndNameById(int id);

    List<Integer> getProvinceId();

    List<Integer> getCitiesIdByProvince(int id);

    List<Map<String,Object>> getNamesAndPicsByComp(int cid);
}
