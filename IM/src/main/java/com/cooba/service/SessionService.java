package com.cooba.service;

import com.cooba.entity.Session;
import com.cooba.entity.User;

import java.time.LocalDateTime;

public interface SessionService {

    Session add(User user, String platform, String ip);

    LocalDateTime remove(User user, String platform);

    Session getInfo(long userId, String platform);
}
