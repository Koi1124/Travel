package com.han.travel.component;


import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/message")
@Component
public class MessageSocketServer
{
    private Session session;
    private static CopyOnWriteArraySet<MessageSocketServer> messageSet=new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session)
    {
        if (session!=null)
        {
            this.session=session;
            messageSet.add(this);
        }else {
            return;
        }
    }

    @OnClose
    public void onClose()
    {
        messageSet.remove(this);
    }

    @OnMessage
    public void onMessage(String message)
    {
        JSONObject jsonObject=JSONObject.fromObject(message);
        int rid=(Integer) jsonObject.get("id");
        int type=(Integer) jsonObject.get("type");
        // 0去除一条消息 1去除所有消息
        if (type==0)
        {
            for (MessageSocketServer m:messageSet)
            {
                m.session.getAsyncRemote().sendText("remove,single,"+String.valueOf(rid));
            }
        }
        else
        {
            for (MessageSocketServer m:messageSet)
            {
                m.session.getAsyncRemote().sendText("remove,all,"+String.valueOf(rid));
            }
        }
    }

    @OnError
    public void onError(Throwable e)
    {
        e.printStackTrace();
    }



    public static void sendMessage(Integer rid)
    {
        for (MessageSocketServer m:messageSet)
        {
            m.session.getAsyncRemote().sendText(String.valueOf(rid));
        }
    }

    public static void sendLetter(Integer rid)
    {
        for (MessageSocketServer m:messageSet)
        {
            m.session.getAsyncRemote().sendText("letter,"+String.valueOf(rid));
        }
    }

}
