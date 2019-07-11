package com.han.travel.component;

import com.han.travel.service.LetterService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(value = "/socket/{userId}")
@Component
public class SocketServer
{
    private Session session;
    private static Map<String,Session> sessionPool=new HashMap<>();
    private static Map<String,String> sessionIds=new HashMap<>();

    
    /**
     *@discription: spring管理的都是单例（singleton），和 websocket （多对象）相冲突
     *@param null 
     *@date: 2019/7/11 12:34
     *@return: 
     *@author: Han
     */
    private static LetterService letterService;


    @Autowired
    public void setLetterService(LetterService letterService)
    {
        SocketServer.letterService=letterService;
    }

    /**
     *@discription: 用户连接时启用
     *@param session
	 *@param userId
     *@date: 2019/7/10 10:14
     *@return: void
     *@author: Han
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId)
    {
        this.session=session;
        sessionPool.put(userId,session);
        sessionIds.put(session.getId(),userId);
    }


    /**
     *@discription: 收到消息时启用
     *@param message 
     *@date: 2019/7/10 10:16
     *@return: void
     *@author: Han
     */
    @OnMessage
    public void onMessage(String message,Session session)
    {
        JSONObject jsonObject=JSONObject.fromObject(message);
        int clientId=Integer.parseInt(sessionIds.get(session.getId()));
        int toClientId=Integer.parseInt(jsonObject.get("To").toString());
        String content=jsonObject.get("message").toString();
        Map<String,Object> dto=new HashMap<>();
        dto.put("clientId",clientId);
        dto.put("toClientId",toClientId);
        dto.put("content",content);
        letterService.insertLettter(dto);

        sendMessageTo(content,String.valueOf(toClientId),String.valueOf(clientId));

    }

    /**
     *@discription: 关闭连接时启用
     *@param  
     *@date: 2019/7/10 10:19
     *@return: void
     *@author: Han
     */
    @OnClose
    public void onClose() 
    {
        sessionPool.remove(sessionIds.get(session.getId()));
        sessionIds.remove(session.getId());
    }

    /**
     *@discription: 发生错误时启用
     *@param  
     *@date: 2019/7/10 10:27
     *@return: void
     *@author: Han
     */
    @OnError
    public void onError(Throwable e)
    {
        e.printStackTrace();
    }

    /**
     *@discription: 用户发送消息
     *@param message
	 *@param toUserId
     *@date: 2019/7/10 10:28
     *@return: void
     *@author: Han
     */
    public static void sendMessageTo(String message,String toUserId, String userId)
    {
        Session session=sessionPool.get(toUserId);
        if (session!=null)
        {
            try
            {
                session.getAsyncRemote().sendText(message+","+toUserId+","+userId);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }
    
    
    /**
     *@discription: 获取当前连接数
     *@param  
     *@date: 2019/7/11 8:48
     *@return: int
     *@author: Han
     */
    public static int getOnlineNum(){
        return sessionPool.size();
    }

    /**
     *@discription: 获取在线用户名以逗号隔开
     *@param  
     *@date: 2019/7/11 8:46
     *@return: java.lang.String
     *@author: Han
     */
    public static String getOnlineUsers() 
    {
        StringBuffer users = new StringBuffer();
        for (String key : sessionIds.keySet()) {
            users.append(sessionIds.get(key)+",");
        }
        return users.toString();
    }


}
