package com.cooba.core;

public interface SocketConnection {


    void bindGroup(String userid, String group);

    void unbindGroup(String userid, String group);

    void sendToUser(String userid, String message);

    void sendToGroup(String group, String message);

    void sendToAll(String message);
}
