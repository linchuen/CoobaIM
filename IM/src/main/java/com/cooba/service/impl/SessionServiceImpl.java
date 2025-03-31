package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.constant.ErrorEnum;
import com.cooba.constant.JwtSecret;
import com.cooba.entity.Session;
import com.cooba.entity.User;
import com.cooba.exception.BaseException;
import com.cooba.repository.SessionRepository;
import com.cooba.service.SessionService;
import com.cooba.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final JwtUtil jwtUtil;
    private final JwtSecret jwtSecret;

    @Override
    public Session add(User user, String platform, String ip) {
        Optional<Session> dbSession = sessionRepository.find(user.getId(), platform);

        LocalDateTime now = LocalDateTime.now();

        Session session = new Session();
        session.setUserId(user.getId());
        session.setPlatform(platform);
        session.setLoginTime(now);
        session.setExpireTime(now.plusDays(jwtSecret.getTtlDay()));
        session.setIp(ip);
        session.setToken(jwtUtil.createToken(user, now));
        session.setEnable(true);

        if (dbSession.isPresent()) {
            sessionRepository.updateByUserIdAndPlatform(session);
        } else {
            sessionRepository.insert(session);
        }

        return session;
    }

    @Override
    public LocalDateTime remove(User user, String platform) {
        LocalDateTime now = LocalDateTime.now();

        Session session = new Session();
        session.setUserId(user.getId());
        session.setLogoutTime(now);
        session.setPlatform(platform);
        session.setEnable(false);
        sessionRepository.updateByUserIdAndPlatform(session);

        return now;
    }

    @Override
    public Session getInfo(long userId, String platform) {
        return sessionRepository.find(userId, platform)
                .orElseThrow(() -> new BaseException(ErrorEnum.SESSION_NOT_EXIST));
    }
}
