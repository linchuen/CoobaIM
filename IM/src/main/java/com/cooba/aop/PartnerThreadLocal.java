package com.cooba.aop;

import org.springframework.stereotype.Component;

@Component
public class PartnerThreadLocal {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public void set(String partner) {
        threadLocal.set(partner == null ? "cooba" : partner);
    }

    public String get() {
        return threadLocal.get();
    }
}
