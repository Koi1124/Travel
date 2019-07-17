package com.han.travel.service;


import com.han.travel.dao.Aa01Dao;
import com.han.travel.support.Utils;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


@Service
public class AdminService
{

    @Resource
    private Aa01Dao aa01Dao;

    /**
     * @Author 
     * @Description 
     * @Date 2019/7/15
     * @param map
     * @return boolean
     **/
    public boolean adminCheck(Map<String, Object> map)
    {
        String username=map.get("username").toString();
        String password=map.get("password").toString();
        if(username.equals("admin") && Utils.encode(password).substring(0,18).equals(aa01Dao.getPwdByName(username)))
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
    
    public boolean updatePwd(Map<String, Object> map)
    {
    	String newPassword=Utils.encode(map.get("newPassword").toString()).substring(0,18);
    	String oldPassword=Utils.encode(map.get("oldPassword").toString()).substring(0,18);
    	return aa01Dao.updateAdminPwd(newPassword,oldPassword);
    }

}
