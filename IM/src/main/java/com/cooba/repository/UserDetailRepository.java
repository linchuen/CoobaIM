package com.cooba.repository;

import com.cooba.entity.UserDetail;

import java.util.List;
import java.util.Optional;

public interface UserDetailRepository extends BaseRepository<UserDetail> {
    List<UserDetail> findByUserId(List<Long> userIds);

    Optional<UserDetail> findByUserId(long userId);
}
