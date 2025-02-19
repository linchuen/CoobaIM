package com.cooba.core.spring;

import com.cooba.constant.RoleEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

@Configuration
public class WebsocketSecurityConfig {

    @Bean
    public AuthorizationManager<Message<?>> messageAuthorizationManager() {
        MessageMatcherDelegatingAuthorizationManager.Builder messages=MessageMatcherDelegatingAuthorizationManager.builder();
        messages
                .nullDestMatcher().authenticated()
                .simpSubscribeDestMatchers("/user/**").hasRole(RoleEnum.USER.getRole())
                .simpDestMatchers("/app/**").hasRole(RoleEnum.USER.getRole())
                .simpSubscribeDestMatchers( "/topic/broadcast").permitAll()
                .anyMessage().denyAll();

        return messages.build();
    }
}
