package com.cooba.util.lock;

import java.util.concurrent.locks.Lock;

public interface LockUtil {

    Lock getLock(String key);
}
