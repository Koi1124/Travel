package com.han.travel.controller;

import com.han.travel.component.SocketServer;
import com.han.travel.service.LetterService;
import com.han.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
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
    @Autowired
    UserService userService;

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


    @GetMapping("/detail/{id}")
    public String toLetterDetail(HttpSession session, @PathVariable("id") Integer id, Map<String,Object> dto)
    {
        System.out.println(id);
        dto.put("detail",letterService.getDetailByClientAndToClient((Integer) session.getAttribute("user_id"),id));
        dto.put("toClient",userService.getUserPicAndNameById(id));
        return "letter/detail";
    }



//    @GetMapping("/detail/2")
//    public String toDetailPage(HttpSession session, Map<String,Object> dto)
//    {
//        session.setAttribute("user_id",1);
//
//        int id=2;
//        //int num=SocketServer.getOnlineNum();
//        //String name=SocketServer.getOnlineUsers();
//        dto.put("detail",letterService.getDetailByClientAndToClient((Integer) session.getAttribute("user_id"),id));
//        dto.put("toClient",userService.getUserPicAndNameById(id));
//        //dto.put("toClientId",id);
////        System.out.println("num:"+num);
////        System.out.println("name:"+name);
//        return "letter/detail";
//    }
//
//    @GetMapping("/detail/1")
//    public String toDetailPage2(HttpSession session,  Map<String,Object> dto)
//    {
//        session.setAttribute("user_id",2);
//
//        int id=1;
//        //int num=SocketServer.getOnlineNum();
//        //String name=SocketServer.getOnlineUsers();
//        dto.put("detail",letterService.getDetailByClientAndToClient((Integer) session.getAttribute("user_id"),id));
//        dto.put("toClient",userService.getUserPicAndNameById(id));
//        System.out.println(dto);
//        //dto.put("toClientId",id);
////        System.out.println("num:"+num);
////        System.out.println("name:"+name);
//        return "letter/detail";
//    }

}
