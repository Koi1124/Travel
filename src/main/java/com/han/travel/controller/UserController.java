package com.han.travel.controller;

import com.han.travel.dao.Aa01Dao;
import com.han.travel.service.IdentifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName UserController
 * @Description TODO
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
        if (identifyCodeService.checkIdentifyCode(map)) {
            aa01Dao.addUser(map);
            identifyCodeService.deleteIdentifyCodeByMail((String)map.get("mail"));
            return true;
        }
        return false;
    }
}
