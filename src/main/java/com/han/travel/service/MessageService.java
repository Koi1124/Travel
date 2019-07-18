package com.han.travel.service;

import com.han.travel.component.MessageSocketServer;
import com.han.travel.dao.Sa01Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService
{
    @Resource
    private Sa01Dao sa01Dao;

    @Autowired
    MessageSocketServer messageSocketServer;


    private void operate(Object userName, String type, Object detail, String action, Object rUserId, Object pid)
    {

        Map<String,Object> dto=new HashMap<>(4);
        dto.put("userId",rUserId);
        dto.put("type",type);
        switch (type)
        {
            case "1":
                type="游记攻略";
                break;
            case "3":
                type="评论";
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
        StringBuilder content=new StringBuilder()
                .append("用户")
                .append(userName)
                .append(action)
                .append("了您的")
                .append(type+"：")
                .append(detail)
                ;
        dto.put("content",content.toString());
        dto.put("pid",pid);
        if (sa01Dao.insertMessage(dto))
        {
            MessageSocketServer.sendMessage((Integer) rUserId);
        }
    }


    public void thumbsUp(Object userName, String type, Object detail, Object rUserId, Object pid)
    {
        operate(userName,type,detail,"点赞",rUserId,pid);
    }


    public void comment(Object userName, String type, Object detail, Object rUserId, Object pid)
    {
        operate(userName,type,detail,"评论",rUserId, pid);
    }

    public void reply(Object userName, String type, Object detail, Object rUserId, Object pid)
    {
        operate(userName,"评论",detail,"回复",rUserId, pid);
    }

    public void collect(Object userName, String type, Object detail, Object rUserId, Object pid)
    {
        operate(userName,type,detail,"收藏",rUserId, pid);
    }

    public Integer getNumByUId(int uid)
    {
        return sa01Dao.getCount(uid);
    }

    
    /**
     *@discription: 得到所有未读消息 map->
     * url: 点击消息后跳转的链接
     * msg: 消息内容
     * id: 消息id 用于删除此消息
     *@param uid 
     *@date: 2019/7/17 19:01
     *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@author: Han
     */
    public List<Map<String,Object>> getAllMessageByUId(int uid)
    {
        List<Map<String,Object>> list=sa01Dao.messageByUId(uid);
        List<Map<String,Object>> result=new ArrayList<>(list.size());
        for (Map<String,Object> m:list)
        {
            Map<String,Object> dto=new HashMap<>(3);
            String url="";
            switch ((String)m.get("type"))
            {
                case "1":
                    url="/note/"+m.get("id")+".html";
                    break;
                case "5":
                    url="/together/company/detail/"+m.get("id")+".html";
                    break;
                default:
                    url="##";
                    break;
            }
            dto.put("url",url);
            dto.put("msg",m.get("msg"));
            dto.put("id",m.get("id"));
            result.add(dto);
        }
        return result;
    }
    
    /**
     *@discription: 已读此消息
     *@param id 
     *@date: 2019/7/17 20:09
     *@return: boolean
     *@author: Han
     */
    public boolean readDoneMessage(int id)
    {
        return sa01Dao.deleteMessage(id);
    }

}
