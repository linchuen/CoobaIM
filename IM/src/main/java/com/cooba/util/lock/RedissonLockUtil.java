package com.cooba.util.lock;

import com.cooba.constant.ErrorEnum;
import com.cooba.entity.Session;
import com.cooba.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class RedissonLockUtil implements LockUtil{
    private final RedissonClient redissonClient;

    @Override
    public Lock getLock(String key) {
        return redissonClient.getLock(key);
    }

    @Override
    public void tryLock(String key, Runnable runnable) {
        RLock lock = redissonClient.getLock(key);
        try {
            boolean acquired = lock.tryLock(5, 10, TimeUnit.SECONDS);
            if (!acquired) throw new BaseException(ErrorEnum.NETWORK_ERROR);

            runnable.run();
        } catch (InterruptedException e) {
            throw new BaseException(ErrorEnum.NETWORK_ERROR);
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
    }

    @Override
    public <T> T tryLock(String key, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(key);
        try {
            boolean acquired = lock.tryLock(5, 10, TimeUnit.SECONDS);
            if (!acquired) throw new BaseException(ErrorEnum.NETWORK_ERROR);

            return supplier.get();
        } catch (InterruptedException e) {
            throw new BaseException(ErrorEnum.NETWORK_ERROR);
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
    }
}
