package com.cooba.aop;

import com.cooba.dto.UserBasicInfo;

public class UserThreadLocal {

    private static final ThreadLocal<UserBasicInfo> threadLocal = new ThreadLocal<>();

    public static void set(UserBasicInfo userBasicInfo){
        threadLocal.set(userBasicInfo);
    }

    public static UserBasicInfo get(){
        return threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }

}
