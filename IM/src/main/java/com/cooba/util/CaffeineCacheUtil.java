package com.cooba.util;

import com.github.benmanes.caffeine.cache.*;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CaffeineCacheUtil implements CacheUtil {
    // 存每個 key 的過期時間（絕對時間戳）
    private final Map<String, Long> ttlMap = new ConcurrentHashMap<>();

    private final Cache<String, String> cache;

    public CaffeineCacheUtil() {
        this.cache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfter(new Expiry<String, String>() {
                    @Override
                    public long expireAfterCreate(String key, String value, long currentTime) {
                        Long expireAt = ttlMap.get(key);
                        if (expireAt == null) return Long.MAX_VALUE;
                        long nanos = (expireAt - System.currentTimeMillis()) * 1_000_000;
                        return Math.max(nanos, 0);
                    }

                    @Override
                    public long expireAfterUpdate(String key, String value,
                                                  long currentTime, long currentDuration) {
                        return expireAfterCreate(key, value, currentTime);
                    }

                    @Override
                    public long expireAfterRead(String key, String value,
                                                long currentTime, long currentDuration) {
                        return currentDuration; // 讀取不影響 TTL
                    }
                })
                .build();
    }

    public void set(String key, String value, Duration timeout) {
        ttlMap.put(key, System.currentTimeMillis() + timeout.toMillis());
        cache.put(key, value);
    }

    public String get(String key) {
        return cache.getIfPresent(key);
    }
}

