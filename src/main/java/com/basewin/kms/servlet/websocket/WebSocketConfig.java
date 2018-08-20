package com.basewin.kms.servlet.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 开启WebSocket支持
 * 如果用外置tomcat，要注释掉以下代码，否则启动项目会报错，用springboot内置tomcat就得放开以下代码
 * @author niuhao
 */
//@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}