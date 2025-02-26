package com.cooba.core;

import com.cooba.entity.Chat;

public interface SocketConnection {


    void bindGroup(String userId, String group);

    void unbindGroup(String userId, String group);

    <T> void sendUserEvent(String userId, String event, T t);

    void sendToUser(String userId, Chat chat);

    void sendToGroup(String group, Chat chat);

    void sendToAll(String message);
}
