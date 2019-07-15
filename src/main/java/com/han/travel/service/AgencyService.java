package com.han.travel.service;

import com.han.travel.configuration.SessionConfig;
import com.han.travel.dao.Aa02Dao;
import com.han.travel.support.ImgUploadTools;
import com.han.travel.support.Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AgencyService
 * @Description 旅行社
 * @Author Saki
 * @Date 2019/7/10
 * @LastUpdate 2019/7/13
 **/
@Service
public class AgencyService
{

    @Resource
    private Aa02Dao aa02Dao;

    /**
     * @Author Saki
     * @Description 单例添加（注册）
     * @Date 2019/7/13
     * @param map
     * @return boolean
     **/
    public boolean addAgency(Map<String, Object> map)
    {
        Map<String, Object> result = aa02Dao.getAgencyByMail((String)map.get("mail"));
        if (Utils.isNotEmpty(result))
        {
            return false;
        }
        else
        {
            map.put("id", null);
            aa02Dao.addAgencyUser(map);
            if (map.get("id") != null)
            {
                aa02Dao.addAgency(map);
                return true;
            }
        }
        return false;
    }

    /**
     * @Author Saki
     * @Description 登录
     * @Date 2019/7/13
     * @param map
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> login(Map<String, Object> map)
    {
        Map<String, Object> user = aa02Dao.getAgencyByMail((String)map.get("mail"));
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
     * @Description
     * @Date 2019/7/10
     * @param aid 旅行社的id
     * @param map 传入的图片base64码列表
     * @return boolean
     **/
    public boolean setLicence(int aid, Map<String, Object> map)
    {
        List<String> images = (ArrayList) map.get("image");
        List<String> paths = ImgUploadTools.uploadImgs(images);

        if (images.size() == paths.size())
        {
            StringBuilder stringBuilder = new StringBuilder();
            for (String path : paths)
            {
                stringBuilder.append(path).append(",");
            }
            //TODO 存入数据库
            return true;
        }
        return false;
    }

}
