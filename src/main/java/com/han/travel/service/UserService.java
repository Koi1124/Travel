package com.han.travel.service;

import com.han.travel.configuration.SessionConfig;
import com.han.travel.dao.Aa01Dao;
import com.han.travel.dao.Aa03Dao;
import com.han.travel.support.ImgUploadTools;
import com.han.travel.support.MailTools;
import com.han.travel.support.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserService
 * @Description 用户service
 * @Author Saki
 * @Date 2019/7/3
 * @LastUpdate 2019/7/10
 **/
@Service
public class UserService {

    @Autowired
    private Aa01Dao aa01Dao;

    @Autowired
    private Aa03Dao aa03Dao;

    /**
     * @Author Saki
     * @Description 用户登录
     *     返回状态码： id 成功； noName 用户名不存在； passFalse 密码错误
     * @Date 2019/7/4
     * @param map
     * @return java.lang.String 
     **/
    public Map<String, Object> login(Map<String, String> map)
    {
        Map<String, Object> user = aa01Dao.getUserByMail(map.get("mail"));
        Map<String, Object> result = new HashMap<>();
        if (Utils.isNotEmpty(user))
        {
            if (map.get("password").equals(user.get("password")))
            {
                result.put("status", "success");
                result.put(SessionConfig.USER_ID, user.get("id"));
                result.put(SessionConfig.USER_LOGO, user.get("pic"));
                result.put(SessionConfig.USER_NAME, user.get("name"));
            }
            else
            {
                result.put("status", "passFalse");
            }
        }
        else {
            result.put("status", "noName");
        }
        return result;
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
     * @Description 根据id获取用户详细信息
     * @Date 2019/7/10
     * @param id
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    public Map<String, Object> getUserById(int id)
    {
        Map result = aa01Dao.getUserById(id);
        if (result.get("address") != null && !result.get("address").equals(""))
        {
            String addressName = aa03Dao.getNameById(Integer.parseInt((String)result.get("address")));
            result.put("addressName", addressName);
        }
        else
        {
            result.put("addressName", "");
        }
        return result;
    }

    /**
     * @Author Saki
     * @Description 更新用户
     * @Date 2019/7/10
     * @param map
     * @return boolean
     **/
    public boolean updateUser(Map<String, String> map)
    {
        String addressName = map.get("address");
        Integer address = aa03Dao.getIdByName(addressName);
        System.out.println(address);
        if (address == null) {
            return false;
        }
        map.put("address", String.valueOf(address));
        System.out.println(map);
        return aa01Dao.updateUser(map);
    }

    /**
     * @Author Saki
     * @Description //TODO
     * @Date 2019/7/10
     * @param map
     * @return boolean
     **/
    public String updateLogo(Map<String, String> map)
    {
        String path = ImgUploadTools.uploadImg(map.get("image"));
        if (path != null && !path.equals(""))
        {
            map.put("image", path);
            if (aa01Dao.updateLogo(map))
            {
                return path;
            }
        }
        return "";
    }


    /**
     * @Author Saki
     * @Description //TODO
     * @Date 2019/7/10
     * @param map
     * @return boolean
     **/
    public boolean updatePassword(Map<String, String> map)
    {
        return aa01Dao.updatePassword(map) > 0;
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
