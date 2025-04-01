package com.cooba.config;

import com.cooba.util.CacheUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import redis.embedded.RedisServer;

import java.io.IOException;

@TestConfiguration
public class MockConfig {
    @Bean
    public CacheUtil cacheUtil() {
        return Mockito.mock(CacheUtil.class);
    }
}
