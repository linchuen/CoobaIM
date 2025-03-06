package com.cooba.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class ConnectionManager {
    private final RedisTemplate<String, String> redisTemplate;
    private final Map<String, String> userMap = new ConcurrentHashMap<>();

    public void addUser(String userId, String sessionId) {
        userMap.put(userId, sessionId);
        redisTemplate.opsForSet().add("socket-connection:" + userId, sessionId);
    }

    public void removeUser(String userId, String sessionId) {
        userMap.remove(userId);
        redisTemplate.opsForSet().remove("socket-connection:" + userId, sessionId);
    }

    public void addGroupUser(String group, String userId, String sessionId) {
        redisTemplate.opsForHash().put("socket-connection:" + group, userId, sessionId);
    }

    public void removeGroupUser(String group, String userId) {
        redisTemplate.opsForHash().delete("socket-connection:" + group, userId);
    }

    public Map<Object, Object> getGroupMembers(String group) {
        return redisTemplate.opsForHash().entries("socket-connection:" + group);
    }

    public boolean isUserOnLocal(String userId) {
        return userMap.containsKey(userId);
    }

    public boolean isUserOnline(String userId) {
        return redisTemplate.hasKey("socket-connection:" + userId);
    }
}
