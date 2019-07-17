package com.han.travel.component;


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
        this.session=session;
        messageSet.add(this);
    }

    @OnClose
    public void onClose()
    {
        messageSet.remove(this);
    }

    @OnMessage
    public void onMessage(String message)
    {

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
}
