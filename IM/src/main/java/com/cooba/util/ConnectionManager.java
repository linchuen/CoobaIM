package com.cooba.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConnectionManager {
    private final RedisTemplate<String, String> redisTemplate;

    public void addUser(String userId, String sessionId) {
        redisTemplate.opsForSet().add("socket-connection:" + userId, sessionId);
    }

    public void removeUser(String userId, String sessionId) {
        redisTemplate.opsForSet().remove("socket-connection:" + userId, sessionId);
    }

    public void addGroupUser(String group, String userId, String sessionId) {
        redisTemplate.opsForHash().put("socket-connection:" + group, userId, sessionId);
    }

    public void removeGroupUser(String group, String userId, String sessionId) {
        redisTemplate.opsForHash().delete("socket-connection:" + group, userId, sessionId);
    }

    public boolean isUserOnline(String userId) {
        return redisTemplate.hasKey("socket-connection:" + userId);
    }
}
