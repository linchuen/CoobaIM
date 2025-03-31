package com.cooba.repository;

import com.cooba.entity.Session;

import java.util.Optional;

public interface SessionRepository extends BaseRepository<Session> {

    void updateByUserIdAndPlatform(Session session);

    Optional<Session> find(long userId, String platform);
}
