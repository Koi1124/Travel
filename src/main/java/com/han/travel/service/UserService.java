package com.han.travel.service;

import com.han.travel.dao.Aa01Dao;
import com.han.travel.support.MailTools;
import com.han.travel.support.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName UserService
 * @Description 用户service
 * @Author Saki
 * @Date 2019/7/3
 * @LastUpdate 2019/7/9
 **/
@Service
public class UserService {

    @Autowired
    private Aa01Dao aa01Dao;

    /**
     * @Author Saki
     * @Description 用户登录
     *     返回状态码： id 成功； noName 用户名不存在； passFalse 密码错误
     * @Date 2019/7/4
     * @param map
     * @return java.lang.String 
     **/
    public String login(Map<String, String> map)
    {
        Map<String, Object> result = aa01Dao.getUserByMail(map.get("mail"));
        if (Utils.isNotEmpty(result))
        {
            if (map.get("password").equals(result.get("password")))
            {
                return String.valueOf(result.get("id"));
            }
            return "passFalse";
        }
        return "noName";
    }

    /**
     * @Author Saki
     * @Description 添加用户（用户注册）
     * @Date 2019/7/8
     * @param map
     * @return boolean
     **/
    public boolean addUser(Map<String, Object> map)
    {
        return aa01Dao.addUser(map);
    }


    /**
     * @Author Saki
     * @Description 根据邮箱发送密码
     * @Date 2019/7/9
     * @param mail
     * @return boolean
     **/
    public boolean sendPasswordByMail(String mail)
    {
        Map<String, Object> result = aa01Dao.getUserByMail(mail);
        if (Utils.isNotEmpty(result))
        {
            String content = "周游旅行平台：\n\t您的密码为：" + result.get("password") + "，请尽快修改密码以确保账号安全。";
            try
            {
                MailTools.sendMail(mail, content);
                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }
}
