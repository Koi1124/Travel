package com.han.travel.controller;

import com.han.travel.dao.Aa01Dao;
import com.han.travel.service.IdentifyCodeService;
import com.han.travel.support.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName UserController
 * @Description 用户信息管理controller
 * @Author Saki
 * @Date 2019/7/3
 * @LastUpdate 2019/7/4
 **/

@Controller
public class UserController
{
    @Autowired
    private IdentifyCodeService identifyCodeService;

    @Autowired
    private Aa01Dao aa01Dao;

    @RequestMapping("/")
    public String welcomeHome()
    {
        return "register";
    }

    /**
     * @Author Saki
     * @Description 用户注册邮箱判断
     *       如果被占用返回true，没有使用返回false
     * @Date 2019/7/3
     * @param map
     * @return boolean
     **/
    @PostMapping("/register/mail_check")
    @ResponseBody
    public boolean checkMail(@RequestBody Map<String, Object> map)
    {
        return identifyCodeService.isMailUsed((String)map.get("mail"));
    }

    /**
     * @Author Saki
     * @Description 获取输入邮箱的验证码
     * @Date 2019/7/3
     * @param map
     * @return boolean
     **/
    @PostMapping("/register/get_identify_code")
    @ResponseBody
    public boolean getIdentifyCode(@RequestBody Map<String, Object> map)
    {
        return identifyCodeService.addIdentifyCode((String)map.get("mail"));
    }

    /**
     * @Author Saki
     * @Description 用户注册事件
     * @Date 2019/7/3
     * @param map
     * @return boolean
     **/
    @PostMapping("/register")
    @ResponseBody
    public boolean register(@RequestBody Map<String, Object> map)
    {
        if (identifyCodeService.checkIdentifyCode(map))
        {
            aa01Dao.addUser(map);
            identifyCodeService.deleteIdentifyCodeByMail((String)map.get("mail"));
            return true;
        }
        return false;
    }

    /**
     * @Author Saki
     * @Description 用户登录
     *     返回状态码： 200 成功； 300 用户名不存在； 400 密码错误
     * @Date 2019/7/4
     * @param map
     * @return java.lang.String
     **/
    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody Map<String, String> map)
    {
        Map<String, String> result = aa01Dao.getUserByMail(map.get("mail"));
        if (Utils.isNotEmpty(result))
        {
            if (map.get("password").equals(result.get("password")))
            {
                return "200";
            }
            return "400";
        }
        return "300";
    }
}
