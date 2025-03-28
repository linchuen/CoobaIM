package com.cooba.config;

import com.cooba.constant.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        JwtSecret.class,
        Password.class,
        FrontEnd.class,
        StompMQ.class,
        Minio.class,
        Livekit.class,
        Webhook.class
})
public class PropertyConfig {
}
