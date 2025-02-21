package com.cooba.repository.impl;

import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.Session;
import com.cooba.mapper.SessionMapper;
import com.cooba.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
public class SessionRepositoryImpl implements SessionRepository {
    private final SessionMapper sessionMapper;

    @Override
    public void insert(Session session) {
        sessionMapper.insert(session);
    }
}