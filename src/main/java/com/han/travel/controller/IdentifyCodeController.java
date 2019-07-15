package com.han.travel.controller;

import com.han.travel.service.IdentifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @ClassName IdentifyCodeServlet
 * @Description 验证码
 * @Author Saki
 * @Date 2019/7/13
 * @LastUpdate 2019/7/13
 **/
@Controller
public class IdentifyCodeController
{

    @Autowired
    private IdentifyCodeService identifyCodeService;


    /**
     * @Author Saki
     * @Description 用户获得验证码
     * @Date 2019/7/13
     * @param map {
     *            mail:
     *            type:
     *        }
     * @return boolean
     **/
    @PostMapping("/get_identify_code")
    @ResponseBody
    public boolean getIdentifyCode(@RequestBody Map<String, Object> map)
    {
        return identifyCodeService.addIdentifyCode(map);
    }

}
