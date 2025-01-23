package com.cooba.service;

import com.cooba.entity.User;

public interface UserService {
    void register(User user);

    void enterRoom();

    void leaveRoom();
}
