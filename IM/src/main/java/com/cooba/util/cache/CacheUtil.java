package com.cooba.util.cache;

import java.time.Duration;

public interface CacheUtil {
    String get(String key);

    void set(String key, String value, Duration timeout);
}
