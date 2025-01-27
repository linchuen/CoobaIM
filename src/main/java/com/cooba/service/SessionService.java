package com.cooba.service;

import com.cooba.entity.Session;
import com.cooba.entity.User;

public interface SessionService {

    void add(User user);

    void remove(User user);

    Session getInfo(long userId);
}
