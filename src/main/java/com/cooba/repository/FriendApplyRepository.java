package com.cooba.repository;

import com.cooba.entity.FriendApply;

import java.util.Optional;

public interface FriendApplyRepository extends BaseRepository<FriendApply> {
    Optional<FriendApply> findFriendApply(FriendApply friendApply);
}
