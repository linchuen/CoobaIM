package com.cooba.repository;

import com.cooba.entity.Friend;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends BaseRepository<Friend> {

    void delete(long userId, long friendId);

    void addRoomId(List<Long> userIds, long roomId);

    List<Friend> find(long userId);

    Optional<Friend> find(long userId,long friendId);
}
