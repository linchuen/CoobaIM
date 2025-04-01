package com.cooba.util;

import java.time.Duration;

public interface CacheUtil {
    String get(String key);

    void set(String key, String value, Duration timeout);
}
