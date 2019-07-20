package com.han.travel.controller;

import com.han.travel.configuration.SessionConfig;
import com.han.travel.service.LetterService;
import com.han.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/letter")
public class LetterController
{
    @Autowired
    LetterService letterService;
    @Autowired
    UserService userService;

    @RequestMapping("/haveLetter")
    @ResponseBody
    public boolean haveLetter(HttpSession session)
    {
        return letterService.haveLetter((Integer)session.getAttribute(SessionConfig.USER_ID));
    }


    @RequestMapping("/readLetter")
    @ResponseBody
    public boolean readDone(@RequestBody Map<String,Object> dto)
    {
        return letterService.readLetter((Integer)dto.get("letter_id"));
    }


    @RequestMapping("/removeChatBar")
    @ResponseBody
    public boolean removeHelper(@RequestBody Map<String,Object> dto)
    {
        return letterService.removeChat((Integer)dto.get("letter_id"));
    }


    /**
     *@discription: 向私信主页传递数据
     * toClient: 发送给的用户-> name: 名字, pic: 头像, id: id
     * time: 时间
     * content: 私信内容
     * state: 已读未读状态
     * letter_id: 帮助删除最新的id
     *@param dto 
     *@date: 2019/7/18 15:35
     *@return: java.lang.String
     *@author: Han
     */
    @RequestMapping({"","/"})
    public String toHomePage(Map<String,Object> dto, HttpSession session)
    {
        List<Map<String,Object>> info=letterService.getLatestLettersByClient((Integer) session.getAttribute(SessionConfig.USER_ID));
        dto.put("info",info);
        return "letter/letterhome";
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
