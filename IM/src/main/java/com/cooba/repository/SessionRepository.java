package com.cooba.repository;

import com.cooba.entity.Session;

import java.util.Optional;

public interface SessionRepository extends BaseRepository<Session> {

    void updateByUserId(Session session);

    Optional<Session> find(long userId);
}
