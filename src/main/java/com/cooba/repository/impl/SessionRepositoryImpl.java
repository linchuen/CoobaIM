package com.cooba.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.Session;
import com.cooba.mapper.SessionMapper;
import com.cooba.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;


@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
public class SessionRepositoryImpl implements SessionRepository {
    private final SessionMapper sessionMapper;

    @Override
    public void insert(Session session) {
        sessionMapper.insert(session);
    }

    @Override
    public void insert(List<Session> sessions) {
        sessionMapper.insert(sessions);
    }

    @Override
    public Session selectById(long id) {
        return sessionMapper.selectById(id);
    }

    @Override
    public void deleteById(long id) {
        sessionMapper.deleteById(id);
    }

    @Override
    public void updateByUserId(Session session) {
        sessionMapper.update(session, new LambdaQueryWrapper<Session>()
                .eq(Session::getUserId, session.getId()));
    }

    @Override
    public Optional<Session> find(long userId) {
        Session session = sessionMapper.selectOne(new LambdaQueryWrapper<Session>()
                .eq(Session::getUserId, userId));
        return Optional.ofNullable(session);
    }
}