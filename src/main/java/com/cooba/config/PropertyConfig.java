package com.cooba.config;

import com.cooba.constant.JwtSecret;
import com.cooba.constant.Password;
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
}
