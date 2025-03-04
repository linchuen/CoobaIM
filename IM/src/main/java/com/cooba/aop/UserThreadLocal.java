package com.cooba.aop;

import com.cooba.dto.UserInfo;
import com.cooba.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserThreadLocal {

    private static final ThreadLocal<UserInfo> threadLocal = new ThreadLocal<>();

    public void set(UserInfo userInfo) {
        threadLocal.set(userInfo);
    }

    public UserInfo get() {
        return threadLocal.get();
    }

    public void remove() {
        threadLocal.remove();
    }

    public long getCurrentUserId(){
        return get().getId();
    }

    public String getCurrentUserName(){
        return get().getName();
    }

    public User getCurrentUser(){
        return get().getOrigin();
    }

    public String getCurrentToken(){
        return get().getToken();
    }
}
