package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.utils.OpenAIAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
@ServerEndpoint(value = "/websocket/chatgpt/{userName}")
public class WebsocketChatGpt {
    public static final List<Session> sessions = new CopyOnWriteArrayList<>();
    public static final Map<Session, String> map = new HashMap<>();
    public static final Map<String, List<Map<String, String>>> msgMap = new HashMap<>();
//    List<Map<String, String>> dataList = new CopyOnWriteArrayList<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName) {
        System.out.println("用户已连接");
        System.out.println(userName);
        System.out.println(session.getId());
        map.put(session, userName);
        sessions.add(session);

    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("用户离线");
        msgMap.remove(map.get(session));
        sessions.remove(session);
        map.remove(session);

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        sessions.remove(session);
    }
    public static void sendMessage(String message, String userName) {
        for (Map.Entry<Session, String> entry : map.entrySet()) {
            Session session = entry.getKey();
            String value = entry.getValue();
            System.out.println("key= " + entry.getKey().getId() + " and value= " + entry.getValue());
            if (value.equals(userName)) {
                System.out.println("key= " + entry.getKey().getId() + " and value= " + entry.getValue());
//                session.getAsyncRemote().sendText(OpenAIAPI.chat(dataList));
            }
        }
    }

    @OnMessage(maxMessageSize = 5000000)
    public static void messageAll1(String message,@PathParam("userName") String userName) {
        for (Map.Entry<Session, String> entry : map.entrySet()) {
            Session session = entry.getKey();
            String value = entry.getValue();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            if (value.equals(userName)) {
                List<Map<String, String>> dataList = new ArrayList<>();;
                if (msgMap.size()!=0) {
                    if (msgMap.get(userName) != null) {
                        if (!msgMap.get(userName).isEmpty()) {
                            dataList = msgMap.get(userName);
                        }
                    }
                }
                //最多支持多少条上下文消息
                if(dataList.size()>6) {
                    dataList.remove(0);
                    dataList.remove(0);
                }

                dataList.add(new HashMap<String, String>(){{
                    put("role", "user");
                    put("content", message);
                }});
                msgMap.put(userName,dataList);
                String ass = OpenAIAPI.chat(msgMap.get(userName));
                session.getAsyncRemote().sendText(ass);
                dataList.add(new HashMap<String, String>(){{
                    put("role", "assistant");
                    put("content", ass);
                }});
            }
        }
    }
}
