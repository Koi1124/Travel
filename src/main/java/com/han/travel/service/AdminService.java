package com.han.travel.service;


import com.han.travel.dao.Aa01Dao;
import com.han.travel.dao.Aa04Dao;
import com.han.travel.support.Utils;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import java.util.Map;


@Service
public class AdminService
{

    @Resource
    private Aa04Dao aa04Dao;

    /**
     * @Author 
     * @Description 
     * @Date 2019/7/15
     * @param map
     * @return boolean
     **/
    public String adminCheck(Map<String, Object> map)
    {
        String username=map.get("username").toString();
        String password=map.get("password").toString();
        Map<String,Object> result=aa04Dao.getPwdAndRoleByName(username);
        if(Utils.encode(password).substring(0,18).equals(result.get("pwd").toString()))
        {
        	return result.get("role").toString();
        }
        else
        {
        	return null;
        }
    }
    
    public boolean updatePwd(Map<String, Object> map)
    {
    	map.put("newPassword",Utils.encode(map.get("newPassword").toString()).substring(0,18));
    	map.put("oldPassword",Utils.encode(map.get("oldPassword").toString()).substring(0,18));
    	return aa04Dao.updateAdminPwd(map);
    }
    
    

}
