package com.rancho.web.admin.websocket;

import com.rancho.web.admin.domain.bo.AdminUserDetails;
import com.rancho.web.admin.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/webSocket/{username}")
@Component
@Slf4j
public class WebSocketServer {

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session){
        log.info("用户：{}上线了",username);
        WebSocketHolder.put(username,session);
    }

    /**
     * 客户端关闭
     */
    @OnClose
    public void onClose(@PathParam("username") String username) throws IOException {
        log.info("用户：{}下线了",username);
        WebSocketHolder.remove(username);
    }

    /**
     * 发生错误
     * @param throwable e
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }

    /**
     * 收到客户端发来消息
     * @param message  消息对象
     */
    @OnMessage
    public void onMessage(String message,Session session) {

    }
}
