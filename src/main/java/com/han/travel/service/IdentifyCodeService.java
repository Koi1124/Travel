package com.han.travel.service;

import com.han.travel.dao.Aa01Dao;
import com.han.travel.dao.IdentifyCodeDAO;
import com.han.travel.support.MailTools;
import com.han.travel.support.Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName IdentifyCodeService
 * @Description 有关验证码的服务
 * @Author Saki
 * @Date 2019/7/3
 * @LastUpdate 2019/7/4
 **/
@Service
public class IdentifyCodeService
{

    @Resource
    private IdentifyCodeDAO identifyCodeDAO;

    @Resource
    private Aa01Dao aa01Dao;

    /**
     * @Author Saki
     * @Description 检测邮箱是否被占有
     * @Date 2019/7/3
     * @param mail 邮箱
     * @return boolean 被占用：true；未使用：false
     **/
    public boolean isMailUsed(String mail)
    {
        return Utils.isNotEmpty(aa01Dao.getUserByMail(mail));
    }

    /**
     * @Author Saki
     * @Description 添加/修改验证码
     * @Date 2019/7/3
     * @param mail 邮箱
     * @return boolean 是否成功
     **/
    public boolean addIdentifyCode(String mail)
    {
        String identifyCode = MailTools.getRandomIdentifyCode();
        Map<String, Object> map = new HashMap<>();
        map.put("mail", mail);
        map.put("code", identifyCode);
        String msg = "周游旅行平台：\n\t您的验证码为 " + identifyCode + " ，请在30分钟之内使用。";
        try {
            MailTools.sendMail(mail, msg);
            if (Utils.isNotEmpty(identifyCodeDAO.getIdentifyCodeByMail(mail))) {
                return identifyCodeDAO.updateIdentifyCode(map);
            } else {
                return identifyCodeDAO.addIdentifyCode(map);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Author Saki
     * @Description 检验验证码是否正确
     * @Date 2019/7/3
     * @param map {"mail":mail, "identifyCode":identifyCode}
     * @return boolean 正确：ture；错误：false；
     **/
    public boolean checkIdentifyCode(Map<String, Object> map)
    {
        Map<String, Object> result = identifyCodeDAO.getIdentifyCodeByMail((String)map.get("mail"));
        if (Utils.isNotEmpty(result))
        {
            if (result.get("code").equals((String)map.get("identifyCode")))
            {
                Date date = (Date) result.get("time");
                Date nDate = new Date();
                //30分钟过期
                if (nDate.getTime() - date.getTime() <= 1000 * 60 * 30)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @Author Saki
     * @Description 根据邮箱删除数据库中验证码
     * @Date 2019/7/3
     * @param mail 邮箱
     * @return boolean
     **/
    public boolean deleteIdentifyCodeByMail(String mail)
    {
        return identifyCodeDAO.deleteIdentifyCodeByMail(mail);
    }
}
