package com.cooba.core.spring;


import com.cooba.constant.FrontEnd;
import com.cooba.constant.StompMQ;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;
    private final FrontEnd frontEnd;
    private final StompMQ stompMQ;


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins(frontEnd.getUrl())
                .setHandshakeHandler(new DefaultHandshakeHandler() {
                    @Override
                    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                        return () -> request.getURI().getQuery();
                    }
                })
                .withSockJS(); // 立即斷開，不使用輪詢;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        if (!stompMQ.getEnable()) {
            registry.enableSimpleBroker("/topic", "/queue", "/group");
        } else {
            registry.enableStompBrokerRelay("/topic", "/queue", "/group")
                    .setRelayHost(stompMQ.getRelayHost()) // ActiveMQ 服務
                    .setRelayPort(stompMQ.getRelayPort())
                    .setClientLogin(stompMQ.getLogin())
                    .setClientPasscode(stompMQ.getPasscode());
        }


        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(jwtHandshakeInterceptor); // 註冊 JWT 攔截器
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        messageConverters.add(new Lz4MessageConverter()); // 添加 LZ4 消息转换器
        return false; // 返回 false，避免 Spring Boot 添加默认的 JSON 转换器
    }
}
