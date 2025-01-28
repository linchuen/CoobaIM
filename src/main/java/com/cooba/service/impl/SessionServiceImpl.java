package com.cooba.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.BehaviorLayer;
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

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final JwtUtil jwtUtil;
    private final JwtSecret jwtSecret;

    @Override
    public Session add(User user) {
        Session dbSession = sessionRepository.selectOne(new LambdaQueryWrapper<Session>()
                .eq(Session::getUserId, user.getId()));

        LocalDateTime now = LocalDateTime.now();

        Session session = new Session();
        session.setUserId(user.getId());
        session.setPlatform("web");
        session.setLoginTime(now);
        session.setExpireTime(now.plusDays(jwtSecret.getTtlDay()));
        session.setIp("127.0.0.1");
        session.setToken(jwtUtil.createToken(user, now));
        session.setEnable(true);

        if (dbSession != null) {
            sessionRepository.update(session, new LambdaQueryWrapper<Session>()
                    .eq(Session::getUserId, user.getId()));
        } else {
            sessionRepository.insert(session);
        }

        return session;
    }

    @Override
    public LocalDateTime remove(User user) {
        LocalDateTime now = LocalDateTime.now();

        Session session = new Session();
        session.setLogoutTime(now);
        session.setEnable(false);
        sessionRepository.update(session, new LambdaQueryWrapper<Session>()
                .eq(Session::getUserId, user.getId()));

        return now;
    }

    @Override
    public Session getInfo(long userId) {
        Session session = sessionRepository.selectOne(new LambdaQueryWrapper<Session>()
                .eq(Session::getUserId, userId));
        if (session == null) throw new BaseException();

        return session;
    }
}
