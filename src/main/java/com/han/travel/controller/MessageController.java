package com.han.travel.controller;

import com.han.travel.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/message")
public class MessageController
{

    @Autowired
    private MessageService messageService;

    @RequestMapping("/messageCount")
    @ResponseBody
    public Integer getUnreadMessageCount(@RequestBody Map<String,Object>dto)
    {
        return messageService.getNumByUId((Integer)dto.get("user_id"));
    }

    @RequestMapping("/detail")
    @ResponseBody
    public List<Map<String,Object>> getDetails(@RequestBody Map<String,Object>dto)
    {
        return messageService.getAllMessageByUId((Integer)dto.get("user_id"));
    }

    @RequestMapping("/checkout")
    @ResponseBody
    public boolean checkout(@RequestBody Map<String,Object> dto)
    {
        return messageService.readDoneMessage((Integer)dto.get("msg_id"));
    }

    @RequestMapping("/clearAll")
    @ResponseBody
    public boolean clear(@RequestBody Map<String,Object> dto)
    {
        return messageService.doneAllMessage((Integer)dto.get("user_id"));
    }

}
