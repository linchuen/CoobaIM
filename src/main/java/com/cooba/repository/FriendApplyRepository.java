package com.cooba.repository;

import com.cooba.entity.FriendApply;

import java.util.Optional;

public interface FriendApplyRepository extends BaseRepository<FriendApply> {
    Optional<FriendApply> findByApplyIdAndPermitId(FriendApply friendApply);

    void updateByApplyIdAndPermitId(FriendApply friendApply);

    void deleteByApplyIdAndPermitId(FriendApply friendApply);

    void deleteByAllApplyIdAndPermitId(FriendApply friendApply);
}
