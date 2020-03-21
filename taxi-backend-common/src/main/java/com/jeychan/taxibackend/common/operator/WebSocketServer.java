package com.jeychan.taxibackend.common.operator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WilderGao
 * time 2020-03-12 12:13
 * motto : everything is no in vain
 * description
 */
@Slf4j
@ServerEndpoint("/taxi_backend/real_time")
@Component
public class WebSocketServer {
    private ConcurrentHashMap<String, Session> clients = new ConcurrentHashMap<>(16);

    @OnOpen
    public void onOpen(Session session) {
        log.info("WebSocketServer.onOpen: new client connect:{}", session.getAsyncRemote());
        clients.put(session.getId(), session);
    }

    @OnMessage
    public synchronized void onMessage(String message) {
        log.info("WebSocketServer.onMessage: receive message :{}", message);
        clients.forEach((id, value) -> {
            if (value.isOpen()) {
                value.getAsyncRemote().sendText("return message " + message);
            } else {
                clients.remove(value.getId());
            }
        });
    }

    /*
     * 很奇怪，自己重写onClose和onError会报错
     */
//    @OnClose
//    public void onClose(Session session) {
//        log.info("WebSocketServer.onClose: one session close, session={}", JSONObject.toJSONString(session));
//        clients.remove(session.getId());
//    }
//
//    /**
//     * 发生错误
//     *
//     * @param throwable e
//     */
//    @OnError
//    public void onError(Session session, Throwable throwable) {
//        log.info("WebSocketServer.onError: session={}, errorMsg={}", JSONObject.toJSONString(session), throwable.getMessage());
//    }
}
