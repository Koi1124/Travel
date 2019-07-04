package com.han.travel.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface Aa01Dao
{
    Map<String, String> getNameAndPicById(int id);

    Map<String, String> getUserByMail(String mail);

    boolean addUser(Map<String, Object> map);
}
