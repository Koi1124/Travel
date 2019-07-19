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
 * @LastUpdate 2019/7/19
 **/

@Controller
public class UserController
{
    @Autowired
    private IdentifyCodeService identifyCodeService;

    @Autowired
    private UserService userService;

    /**
     * @Author Saki
     * @Description 个人设置页面
     * @Date 2019/7/19
     * @param session
     * @param dto
     * @return java.lang.String
     **/
    @RequestMapping("/setting")
    public String setting(HttpSession session, Map<String, Object> dto)
    {
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
    @PostMapping("/user/register/mail_check")
    @ResponseBody
    public boolean checkMail(@RequestBody Map<String, Object> map)
    {
        return identifyCodeService.isMailUsed((String)map.get("mail"));
    }


    /**
     * @Author Saki
     * @Description 用户注册事件
     * @Date 2019/7/3
     * @param map
     * @return boolean
     **/
    @PostMapping("/user/register")
    @ResponseBody
    public boolean register(@RequestBody Map<String, Object> map)
    {
        if (identifyCodeService.checkIdentifyCode(map))
        {
            if (userService.addUser(map))
            {
                identifyCodeService.deleteIdentifyCodeByMail(map);
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
    @PostMapping("/user/login")
    @ResponseBody
    public String login(@RequestBody Map<String, String> map, HttpSession session)
    {

        Map<String, Object> result = userService.login(map);
        switch ((String)result.get("status"))
        {
            case "noName":
                return "300";
            case "passFalse":
                return "400";
            default:
                session.setAttribute(SessionConfig.USER_ID, result.get(SessionConfig.USER_ID));
                session.setAttribute(SessionConfig.USER_NAME, result.get(SessionConfig.USER_NAME));
                session.setAttribute(SessionConfig.USER_LOGO, result.get(SessionConfig.USER_LOGO));
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
    @PostMapping("/user/forget")
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

    /**
     * @Author Saki
     * @Description 修改头像
     * @Date 2019/7/11
     * @param map
     * @param session
     * @return boolean
     **/
    @PostMapping("/change_logo")
    @ResponseBody
    public boolean updateLogo(@RequestBody Map<String, String> map, HttpSession session)
    {
        map.put("id", String.valueOf(session.getAttribute(SessionConfig.USER_ID)));
        String path = userService.updateLogo(map);
        if (path.equals(""))
        {
            return false;
        }
        session.setAttribute(SessionConfig.USER_LOGO, path);
        return true;
    }

    /**
     * @Author Saki
     * @Description 修改用户密码
     * @Date 2019/7/11
     * @param map
     * @param session
     * @return boolean
     **/
    @PostMapping("/change_password")
    @ResponseBody
    public boolean updatePassword(@RequestBody Map<String, String> map, HttpSession session)
    {
        map.put("id", String.valueOf(session.getAttribute(SessionConfig.USER_ID)));
        return userService.updatePassword(map);
    }

    /**
     * @Author Saki
     * @Description 用户个人中心
     * @Date 2019/7/19
     * @param id
     * @param part
     * @return java.lang.String
     **/
    @RequestMapping("/u/{id}/{part}")
    public String userHome(@PathVariable("id") int id, @PathVariable("part") String part,
                           Map<String, Object> dto, HttpSession session)
    {
        dto.put("part", part);
        dto.put("uid", id);
        dto.putAll(userService.getUserDetailsById(id, (Integer)session.getAttribute(SessionConfig.USER_ID), part));
        return "/home/userHome/" + part;
    }

    /**
     * @Author Saki
     * @Description 获取用户关注页面
     * @Date 2019/7/19
     * @param id 用户id
     * @param dto
     * @param session
     * @return java.lang.String
     **/
    @RequestMapping("/follow/{id}")
    public String userFollowsAndFans(@PathVariable("id") int id, Map<String, Object> dto, HttpSession session)
    {
        dto.put("uid", id);
        dto.putAll(userService.getUserFollowsAndFans(id, (Integer)session.getAttribute(SessionConfig.USER_ID)));
        return "/home/follow";
    }

    /**
     * @Author Saki
     * @Description 获取用户关注页面
     * @Date 2019/7/19
     * @param id 用户id
     * @param dto
     * @param session
     * @return java.lang.String
     **/
    @RequestMapping("/fan/{id}")
    public String userFansAndFollows(@PathVariable("id") int id, Map<String, Object> dto, HttpSession session)
    {
        dto.put("uid", id);
        dto.put("isFan", true);
        dto.putAll(userService.getUserFollowsAndFans(id, (Integer)session.getAttribute(SessionConfig.USER_ID)));
        return "/home/follow";
    }
}
