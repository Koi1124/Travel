package com.han.travel.controller;

import com.han.travel.configuration.SessionConfig;
import com.han.travel.service.IdentifyCodeService;
import com.han.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    private UserService userService;

    @RequestMapping("/")
    public String welcomeHome()
    {
        return "testUpload";
    }

    @RequestMapping("/setting")
    public String setting(HttpSession session, Map<String, Object> dto)
    {
        //TODO 删除测试数据
        session.setAttribute(SessionConfig.USER_ID, 0);
        session.setAttribute(SessionConfig.USER_NAME, "admin");
        session.setAttribute(SessionConfig.USER_LOGO, "/asserts/img/commom/default_logo.jpg");

        dto.putAll(userService.getUserById((int)session.getAttribute(SessionConfig.USER_ID)));
        return "setting";
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
            if (userService.addUser(map))
            {
                identifyCodeService.deleteIdentifyCodeByMail((String) map.get("mail"));
                return true;
            }
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
    public String login(@RequestBody Map<String, String> map, HttpServletRequest request)
    {
        String code = userService.login(map);
        switch (code)
        {
            case "noName":
                return "300";
            case "passFalse":
                return "400";
            default:
                request.getSession().setAttribute(SessionConfig.USER_ID, code);
                return "200";
        }
    }

    /**
     * @Author Saki
     * @Description 忘记密码之后发邮件的请求
     * @Date 2019/7/10
     * @param map
     * @return boolean
     **/
    @PostMapping("/forget")
    @ResponseBody
    public boolean forget(@RequestBody Map<String, String> map)
    {
        return userService.sendPasswordByMail(map.get("mail"));
    }
    
    /**
     * @Author Saki
     * @Description 更改信息 昵称、性别、居住地、备注
     * @Date 2019/7/10 
     * @param map
     * @return boolean 
     **/
    @PostMapping("/change_info")
    @ResponseBody
    public boolean updateUser(@RequestBody Map<String, String> map, HttpSession session)
    {
        map.put("id", String.valueOf(session.getAttribute(SessionConfig.USER_ID)));
        return userService.updateUser(map);
    }


    @PostMapping("/change_logo")
    @ResponseBody
    public boolean updateLogo(@RequestBody Map<String, String> map, HttpSession session) {
        map.put("id", String.valueOf(session.getAttribute(SessionConfig.USER_ID)));
        return userService.updateLogo(map);
    }
}
