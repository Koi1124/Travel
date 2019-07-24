package com.han.travel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController
{
    @Override
    public String getErrorPath()
    {
        return "/error";
    }

    @RequestMapping("/error")
    public String handle(HttpServletRequest request)
    {
        Object status=request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status!=null)
        {
            
            /**
             *@discription: 自定义错误页面
             * 若错误编号为404，可跳转至templates下的404.html
             * 500跳至500.html
             * 其他跳转至error.html
             * 此项目只有一个error.html，故不用此方法
             *@param request 
             *@date: 2019/7/23 11:32
             *@return: java.lang.String
             *@author: Han
             */
//            Integer statusCode=Integer.valueOf(status.toString());
//            if (statusCode==HttpStatus.NOT_FOUND.value())
//            {
//                return "404";
//            }
//            else if (statusCode==HttpStatus.INTERNAL_SERVER_ERROR.value())
//            {
//                return "500";
//            }
        }
        return "error";
    }


}
