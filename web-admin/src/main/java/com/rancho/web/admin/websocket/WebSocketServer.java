package com.rancho.web.admin.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rancho.web.admin.domain.bo.AdminUserDetails;
import com.rancho.web.admin.domain.dto.deploy.DeployInfo;
import com.rancho.web.admin.util.SecurityUtils;
import com.rancho.web.admin.util.ShellUtil;
import com.rancho.web.admin.util.SpringBeanFactory;
import com.rancho.web.admin.util.StringUtils;
import com.rancho.web.db.domain.Deploy;
import com.rancho.web.db.domain.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

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
    public void onMessage(String message,Session session) throws IOException {
        try {
            JSONObject jsonObject=JSONObject.parseObject(message);
            //ObjectMapper mapper = new ObjectMapper();
            //Msg newFriend = mapper.readValue(message, Msg.class);
            String type=jsonObject.getString("type");
            String data=jsonObject.getString("data");
            switch (type){
                case "log":
                    JSONObject jb=JSONObject.parseObject(data);
                    Integer count=jb.getInteger("count");
                    DeployInfo deployInfo=JSONObject.parseObject(jb.getString("deploy"), DeployInfo.class);
                    List<Server> servers=deployInfo.getServers();
                    Map<String,Vector> resMap=new HashMap<>();
                    for (Server server:servers){
                        ShellUtil shellUtil=new ShellUtil(server.getIp(),server.getAccount(),server.getPassword(),server.getPort());
                        Vector<String> res=shellUtil.executeForCollect("tail -n"+count+" /web/soft/tomcat/apache-tomcat-8.5.43-8080/logs/catalina.out");
                        resMap.put(server.getIp(),res);
                    }
                    session.getBasicRemote().sendText(JSON.toJSONString(resMap));
                    break;
                case "monitor":

                    break;
                default:
            }
        }catch (Exception e){
            e.printStackTrace();
            session.getBasicRemote().sendText("无效参数");
        }
    }
}
