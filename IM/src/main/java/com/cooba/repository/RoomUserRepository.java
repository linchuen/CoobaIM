package com.cooba.repository;

import com.cooba.constant.RoomRoleEnum;
import com.cooba.entity.RoomUser;

import java.util.List;
import java.util.Optional;

public interface RoomUserRepository extends BaseRepository<RoomUser> {
    void delete(long roomId);

    void delete(long roomId, long userId);

    void delete(long roomId, List<Long> userIds);

    void update(long roomId, long userId, RoomRoleEnum roleEnum);

    List<RoomUser> find(long roomId);

    List<RoomUser> findByUserId(long userId);

    Optional<RoomUser> find(long roomId, long userId);
}
