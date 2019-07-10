package com.han.travel.component;

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
    private static Map<Integer,Session> sessionPool=new HashMap<>();
    private static Map<String,Integer> sessionIds=new HashMap<>();


    /**
     *@discription: 用户连接时启用
     *@param session
	 *@param userId
     *@date: 2019/7/10 10:14
     *@return: void
     *@author: Han
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") int userId)
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
    public void onMessage(String message)
    {
        
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
	 *@param userId 
     *@date: 2019/7/10 10:28
     *@return: void
     *@author: Han
     */
    public static void sendMessage(String message,int userId)
    {
        Session session=sessionPool.get(userId);
        if (session!=null)
        {
            try
            {
                session.getBasicRemote().sendText(message);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }


}
