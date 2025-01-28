package com.cooba.aop;

import com.cooba.dto.UserInfo;

public class UserThreadLocal {

    private static final ThreadLocal<UserInfo> threadLocal = new ThreadLocal<>();

    public static void set(UserInfo userInfo){
        threadLocal.set(userInfo);
    }

    public static UserInfo get(){
        return threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }

}
