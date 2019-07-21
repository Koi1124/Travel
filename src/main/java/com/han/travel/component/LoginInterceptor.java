package com.han.travel.component;

import com.han.travel.configuration.SessionConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        Object user=request.getSession().getAttribute(SessionConfig.USER_ID);
        if (user==null)
        {
            request.setAttribute("msg","请先登录");
            request.getRequestDispatcher("/login.html").forward(request, response);
            return false;
        }
        else
        {
            return true;
        }
    }
}
