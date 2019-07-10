package com.han.travel.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface Aa01Dao
{
    Map<String, Object> getNameAndPicAndIdById(int id);

    Map<String, Object> getUserByMail(String mail);

    boolean addUser(Map<String, Object> map);
}
