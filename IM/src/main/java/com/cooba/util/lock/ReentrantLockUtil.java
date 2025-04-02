package com.cooba.util.lock;

import com.cooba.constant.ErrorEnum;
import com.cooba.exception.BaseException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public class ReentrantLockUtil implements LockUtil {

    @Override
    public Lock getLock(String key) {
        return new ReentrantLock();
    }

    @Override
    public void tryLock(String key, Runnable runnable) {
        Lock lock = getLock(key);
        try {
            boolean acquired = lock.tryLock(5, TimeUnit.SECONDS);
            if (!acquired) {
                throw new BaseException(ErrorEnum.NETWORK_ERROR);
            }
            runnable.run();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BaseException(ErrorEnum.NETWORK_ERROR);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public <T> T tryLock(String key, Supplier<T> supplier) {
        Lock lock = getLock(key);
        try {
            boolean acquired = lock.tryLock(5, TimeUnit.SECONDS);
            if (!acquired) {
                throw new BaseException(ErrorEnum.NETWORK_ERROR);
            }
            return supplier.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BaseException(ErrorEnum.NETWORK_ERROR);
        } finally {
            lock.unlock();
        }
    }
}
