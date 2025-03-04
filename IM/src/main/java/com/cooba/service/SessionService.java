package com.cooba.service;

import com.cooba.entity.Session;
import com.cooba.entity.User;

import java.time.LocalDateTime;

public interface SessionService {

    Session add(User user);

    LocalDateTime remove(User user);

    Session getInfo(long userId);
}
