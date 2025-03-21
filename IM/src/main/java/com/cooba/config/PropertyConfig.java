package com.cooba.config;

import com.cooba.constant.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyConfig {

    @Bean
    @ConfigurationProperties(prefix = "jwt.secret")
    public JwtSecret jwtSecret(){
        return new JwtSecret();
    }

    @Bean
    @ConfigurationProperties(prefix = "password")
    public Password password(){
        return new Password();
    }

    @Bean
    @ConfigurationProperties(prefix = "front-end")
    public FrontEnd frontEnd(){
        return new FrontEnd();
    }

    @Bean
    @ConfigurationProperties(prefix = "stomp.mq")
    public StompMQ stompMQ(){
        return new StompMQ();
    }

    @Bean
    @ConfigurationProperties(prefix = "minio")
    public Minio minio(){
        return new Minio();
    }

    @Bean
    @ConfigurationProperties(prefix = "livekit")
    public Livekit livekit(){
        return new Livekit();
    }

    @Bean
    @ConfigurationProperties(prefix = "webhook")
    public Webhook webhook(){
        return new Webhook();
    }

}
