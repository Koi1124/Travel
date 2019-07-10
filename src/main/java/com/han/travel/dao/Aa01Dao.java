package com.han.travel.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface Aa01Dao
{
    Map<String, String> getNameAndPicById(int id);

    Map<String, String> getUserById(int id);

    Map<String, Object> getUserByMail(String mail);

    boolean updateUser(Map<String, String> map);

    boolean updateLogo(Map<String, String> map);

    boolean addUser(Map<String, Object> map);
}
