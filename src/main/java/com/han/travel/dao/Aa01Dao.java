package com.han.travel.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface Aa01Dao
{
    /**
     *@discription: dto->
     * id: 用户id
     * pic: 用户头像
     * name: 用户名
     *@param id 
     *@date: 2019/7/19 14:52
     *@return: java.util.Map<java.lang.String,java.lang.Object>
     *@author: Han
     */
    Map<String, Object> getNameAndPicAndIdById(int id);

    Map<String, String> getUserById(int id);

    Map<String, Object> getUserByMail(String mail);

    boolean updateUser(Map<String, String> map);

    boolean updateLogo(Map<String, String> map);

    Integer updatePassword(Map<String, String> map);
    
    boolean addUser(Map<String, Object> map);
    
    Integer selectCount();
}
