package com.cooba.config;

import com.cooba.util.CacheUtil;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockConfig {
    @Bean
    public CacheUtil cacheUtil() {
        return Mockito.mock(CacheUtil.class);
    }
}
