package com.han.travel.service;

import com.han.travel.component.MessageSocketServer;
import com.han.travel.dao.Sa01Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageService
{
    @Resource
    private Sa01Dao sa01Dao;

    @Autowired
    MessageSocketServer messageSocketServer;


    private void operate(String userName, String type, String detail, String action,Integer rUserId)
    {
        StringBuilder content=new StringBuilder()
                .append("用户")
                .append(userName)
                .append(action)
                .append("了您的")
                .append(type+"：")
                .append(detail)
                ;
        if (sa01Dao.insertMessage(rUserId,content.toString()))
        {
            MessageSocketServer.sendMessage(rUserId);
        }
    }


    public void thumbsUp(String userName, String type, String detail, Integer rUserId)
    {
        switch (type)
        {
            case "1":
                type="游记攻略";
                break;
            case "3":
                type="评论";
                break;
            default:
                type="";
                break;
        }
        operate(userName,type,detail,"点赞",rUserId);

    }


    public void comment(String userName, String type,String detail,Integer rUserId)
    {
        switch (type)
        {
            case "1":
                type="游记攻略";
                break;
            case "4":
                type="旅行项目";
                break;
            case "5":
                type="结伴";
                break;
            default:
                type="";
                break;
        }

        operate(userName,type,detail,"评论",rUserId);
    }

    public void reply(String userName, String detail, Integer rUserId)
    {
        operate(userName,"评论",detail,"回复",rUserId);
    }

    public void collect(String userName, String type,String detail, Integer rUserId)
    {
        switch (type)
        {
            case "1":
                type="游记攻略";
                break;
            case "4":
                type="旅行项目";
                break;
            case "5":
                type="结伴";
                break;
            default:
                type="undefined";
                break;
        }
        operate(userName,type,detail,"收藏",rUserId);
    }

    public Integer getNumByUId(int uid)
    {
        return sa01Dao.getCount(uid);
    }

    public List<String> getAllMessageByUId(int uid)
    {
        return sa01Dao.messageByUId(uid);
    }


}
