package com.cooba.repository;

import com.cooba.entity.UserDetail;

import java.util.List;

public interface UserDetailRepository extends BaseRepository<UserDetail> {
    List<UserDetail> findByUserId(List<Long> userIds);
}
