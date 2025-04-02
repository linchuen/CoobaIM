package com.cooba.service;

import com.cooba.constant.PlatformEnum;
import com.cooba.entity.Session;
import com.cooba.entity.User;

import java.time.LocalDateTime;

public interface SessionService {

    Session add(User user, PlatformEnum platform, String ip);

    Session refresh(User user, String currentToken, PlatformEnum platform, String ip);

    LocalDateTime remove(User user, PlatformEnum platform);

    Session getInfo(long userId, PlatformEnum platform);
}
