package com.cooba.config;

import com.cooba.util.CacheUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockConfig {
    @Bean
    public CacheUtil cacheUtil() {
        return new CacheUtil(null);
    }
}
