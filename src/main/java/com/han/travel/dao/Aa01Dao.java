package com.han.travel.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface Aa01Dao
{
    Map<String,String> getNameAndPicById(int id);
}
