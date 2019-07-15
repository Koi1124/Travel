package com.han.travel.controller;

import com.han.travel.configuration.SessionConfig;
import com.han.travel.service.AgencyService;
import com.han.travel.service.IdentifyCodeService;
import com.han.travel.support.ImgUploadTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AgencyController
 * @Description 旅行社用户控制器
 * @Author Saki
 * @Date 2019/7/9
 * @LastUpdate 2019/7/9
 **/
@Controller
public class AgencyController
{
    @Autowired
    private AgencyService agencyService;

    @Autowired
    private IdentifyCodeService identifyCodeService;

    /**
     * @Author Saki
     * @Description 检测邮箱是否占用
     * @Date 2019/7/13
     * @param map {
     *            mail：
     *        }
     * @return boolean
     **/
    @PostMapping("/agency/register/mail_check")
    @ResponseBody
    public boolean checkMail(@RequestBody Map<String, Object> map)
    {
        return identifyCodeService.isAgencyMailUsed((String)map.get("mail"));
    }

    /**
     * @Author Saki
     * @Description 旅行社注册事件
     * @Date 2019/7/3
     * @param map {
     *           type:
     *           mail:
     *           password:
     *        }
     * @return boolean
     **/
    @PostMapping("/agency/register")
    @ResponseBody
    public boolean register(@RequestBody Map<String, Object> map)
    {
        System.out.println(map);
        if (identifyCodeService.checkIdentifyCode(map))
        {
            if (agencyService.addAgency(map))
            {
                identifyCodeService.deleteIdentifyCodeByMail(map);
                return true;
            }
        }
        return false;
    }

    /**
     * @Author Saki
     * @Description
     * @Date 2019/7/13
     * @param map {
     *           mail:
     *           password:
     *        }
     * @param session
     * @return java.lang.String
     **/
    @PostMapping("/agency/login")
    @ResponseBody
    public String login(@RequestBody Map<String, Object> map, HttpSession session)
    {
        Map<String, Object> result = agencyService.login(map);
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


    @PostMapping("/agency/setting")
    public String agencySetting(HttpSession session, Map<String, Object> dto)
    {

        return "agency/setting";
    }


    /**
     * @Author Saki
     * @Description 上传图片
     * @Date 2019/7/9
     * @param map
     * @return boolean
     **/
    @PostMapping("/agency/upload_img")
    @ResponseBody
    public boolean uploadImg(@RequestBody Map<String, Object> map)
    {
        List<String> images = (ArrayList) map.get("image");
        List<String> paths = ImgUploadTools.uploadImgs(images);

        if (images.size() == paths.size())
        {
            return true;
        }
        return false;
    }
}
