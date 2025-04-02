package com.cooba.util.lock;

import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

public interface LockUtil {

    Lock getLock(String key);

    void tryLock(String key, Runnable runnable);

    <T> T tryLock(String key, Supplier<T> supplier);
}
