package com.cooba.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ConnectionManager {
    private final RedisTemplate<String, String> redisTemplate;

    public void addUser(String userId, String sessionId) {
        redisTemplate.opsForValue().set("socket-connection:" + userId, sessionId,10, TimeUnit.MINUTES);
    }

    public void removeUser(String userId) {
        redisTemplate.delete("socket-connection:" + userId);
    }

    public boolean isUserOnline(String userId) {
        return redisTemplate.hasKey("socket-connection:" + userId);
    }
}
