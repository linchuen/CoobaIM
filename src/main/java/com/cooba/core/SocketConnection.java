package com.cooba.core;

import com.cooba.entity.Chat;

public interface SocketConnection {


    void bindGroup(String userid, String group);

    void unbindGroup(String userid, String group);

    void sendToUser(String userid, Chat chat);

    void sendToGroup(String group, Chat chat);

    void sendToAll(String message);
}
