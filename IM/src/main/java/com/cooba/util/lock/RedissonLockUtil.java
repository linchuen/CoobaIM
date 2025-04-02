package com.cooba.util.lock;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;

@Component
@RequiredArgsConstructor
public class RedissonLockUtil implements LockUtil{
    private final RedissonClient redissonClient;

    @Override
    public Lock getLock(String key) {
        return redissonClient.getLock(key);
    }
}
