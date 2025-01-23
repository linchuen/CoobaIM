package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.entity.Session;
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
    public void add() {
        sessionRepository.insert(new Session());
    }

    @Override
    public void remove() {
        sessionRepository.deleteById(new Session());
    }
}
