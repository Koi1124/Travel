package com.han.travel.controller;

import com.han.travel.component.SocketServer;
import com.han.travel.service.LetterService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/letter")
public class LetterController
{
    @Autowired
    private SocketServer socketServer;

    @Autowired
    LetterService letterService;

    @RequestMapping("")
    public String toHomePage(Map<String,Object> dto)
    {
        dto.put("content","私信测试");
        dto.put("clientId",2);
        dto.put("toClientId",3);
        letterService.insertLettter(dto);
        List<Map<String,Object>> test=letterService.getLatestLettersByClient(2);
        System.out.println(test);
        return "letter/homepage";
    }


    @GetMapping("/detail/2")
    public String toDetailPage(HttpSession session, Map<String,Object> dto)
    {
        session.setAttribute("userId",1);

        int id=2;
        //int num=SocketServer.getOnlineNum();
        //String name=SocketServer.getOnlineUsers();
        dto.put("detail",letterService.getDetailByClientAndToClient((Integer) session.getAttribute("userId"),id));
        System.out.println(dto);
        dto.put("toClientId",id);
//        System.out.println("num:"+num);
//        System.out.println("name:"+name);
        return "letter/detail";
    }

    @GetMapping("/detail/1")
    public String toDetailPage2(HttpSession session,  Map<String,Object> dto)
    {
        session.setAttribute("userId",2);

        int id=1;
        //int num=SocketServer.getOnlineNum();
        //String name=SocketServer.getOnlineUsers();
        dto.put("detail",letterService.getDetailByClientAndToClient((Integer) session.getAttribute("userId"),id));
        System.out.println(dto);
        dto.put("toClientId",id);
//        System.out.println("num:"+num);
//        System.out.println("name:"+name);
        return "letter/detail";
    }

}
