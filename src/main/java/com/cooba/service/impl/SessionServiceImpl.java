package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.entity.Session;
import com.cooba.exception.BaseException;
import com.cooba.repository.SessionRepository;
import com.cooba.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    @Override
    public void add(Session session) {
        sessionRepository.insert(session);
    }

    @Override
    public void remove(Session session) {
        sessionRepository.deleteById(session);
    }

    @Override
    public Session getInfo(long userId) {
        Session session = sessionRepository.selectById(userId);
        if (session == null) throw new BaseException();

        return session;
    }
}
