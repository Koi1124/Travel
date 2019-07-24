package com.han.travel.service;

import com.han.travel.configuration.SessionConfig;
import com.han.travel.dao.*;
import com.han.travel.support.ImgUploadTools;
import com.han.travel.support.MailTools;
import com.han.travel.support.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @Autowired
    private Ab01Dao ab01Dao;
    @Autowired
    private Ad03Dao ad03Dao;
    @Autowired
    private Ab05Dao ab05Dao;
    @Autowired
    private Ac00Dao ac00Dao;
    @Autowired
    private Ab03Dao ab03Dao;
    @Autowired
    private Ab02Dao ab02Dao;
    @Autowired
    private Ac09Dao ac09Dao;

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
     * @Description 更新头像
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
     * @Description 更新密码
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
     * @Description 获得详细信息，对应个人空间中信息
     * @Date 2019/7/19
     * @param uid 空间主人id
     * @param myId 自己id
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> getUserDetailsById(int uid, Integer myId, String part)
    {
        Map<String, Object> map = getUserById(uid);
        if (map.get("address") == null)
        {
            map.put("address", "保密");
        }
        else
        {
            Integer poi = Integer.valueOf((String) map.get("address"));
            if (poi != null)
            {
                map.put("address", aa03Dao.getNameById(poi));
            }
        }
        if (myId != null && ad03Dao.isFollow(uid, myId) != null)
        {
            map.put("followId", 1);
        }
        map.put("noteCount", ab01Dao.getNoteCountByUid(uid));
        List<Map<String, Object>> list = ad03Dao.getFollows(uid);
        if (list == null)
        {
            map.put("followCount", 0);
        }
        else
        {
            map.put("followCount", list.size());
        }
        map.put("follows", list);
        list = ad03Dao.getFans(uid);
        if (list == null)
        {
            map.put("fanCount", 0);
        }
        else
        {
            map.put("fanCount", list.size());
        }
        map.put("fans", list);


        switch (part) {
            case "note":
                map.put("notes", ab01Dao.getNoteByUid(uid));
                break;
            case "together":
                List<Map<String,Object>> compInfo=ab05Dao.getCompInfoByPublishUId(uid);
                List<Map<String,Object>> compCInfo=ab05Dao.getCompInfoByJoinUId(uid);
                compInfo.addAll(compCInfo);
                map.put("companies",compInfo);
                break;
            case "collectNote":
                map.put("collectNotes",ab01Dao.getCollectNotesByUId(uid));
                break;
            case "collectSight":
                map.put("collectSights", ab03Dao.getCollectSightInfoByUId(uid));
                break;
            case "comment":
                List<Map<String,Object>> commentInfo=ac00Dao.getCompanyCommentInfoByUId(uid);
                List<Map<String,Object>> noteComment=ac00Dao.getNoteCommentInfoByUId(uid);
                List<Map<String,Object>> sightComment=ac00Dao.getSightCommentInfoByUId(uid);
                List<Map<String,Object>> sysComment=ac00Dao.getSysCommentInfoByUId(uid);
                commentInfo.addAll(noteComment);
                commentInfo.addAll(sightComment);
                commentInfo.addAll(sysComment);
                map.put("comments",commentInfo);
                break;
            case "collectRoute":
                map.put("strategys", getStrategyByUid(uid));
                break;
            default:
                break;
        }
        return map;
    }

    /**
     * @Author Saki
     * @Description 用户关注和被关注，额外获取自己有没有关注
     * @Date 2019/7/19
     * @param uid
     * @param myId
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> getUserFollowsAndFans(Integer uid, Integer myId)
    {
        if (myId == null)
        {
            myId = -1;
        }
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = ad03Dao.getUserFollows(uid, myId);
        if (list == null)
        {
            map.put("followCount", 0);
        }
        else
        {
            map.put("followCount", list.size());
        }
        map.put("follows", list);
        list = ad03Dao.getUserFans(uid, myId);
        if (list == null)
        {
            map.put("fanCount", 0);
        }
        else
        {
            map.put("fanCount", list.size());
        }
        map.put("fans", list);
        return map;
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


    public Map<String,Object> getUserPicAndNameById(int id)
    {
        return aa01Dao.getNameAndPicAndIdById(id);
    }

    /**
     * @Author Saki
     * @Description 根据用户的id来获取用户收藏的系统攻略
     * @Date 2019/7/23
     * @param uid
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    private List<Map<String, Object>> getStrategyByUid(int uid)
    {
        List<Map<String, Object>> list = ab02Dao.getStrategyByUid(uid);
        for (Map<String, Object> map : list)
        {
            int rid = Integer.valueOf(map.get("rid").toString());
            List<Map<String, Object>> routes = ac09Dao.getRoutesBySid(rid);
            List<List<Map<String, Object>>> routeList = new ArrayList<>();

            for (int i = 0; i < routes.size(); i++)
            {
                List<Map<String, Object>> poiList = new ArrayList<>();
                String[] pois = routes.get(i).get("pois").toString().split(",");
                for (String poi : pois)
                {
                    poiList.add(ab03Dao.getSightIntroById(Integer.valueOf(poi)));
                }
                routeList.add(poiList);
            }
            map.put("routes", routeList);
        }
        return list;
    }

}
