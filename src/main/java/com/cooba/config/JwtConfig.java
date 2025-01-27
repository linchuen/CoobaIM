package com.cooba.config;

import com.cooba.constant.JwtSecret;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    @ConfigurationProperties(prefix = "jwt.secret")
    public JwtSecret jwtSecret(){
        return new JwtSecret();
    }
}
