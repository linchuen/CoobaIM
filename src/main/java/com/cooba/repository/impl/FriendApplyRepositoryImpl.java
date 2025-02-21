package com.cooba.repository.impl;

import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.FriendApply;
import com.cooba.mapper.FriendApplyMapper;
import com.cooba.repository.FriendApplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;


@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
public class FriendApplyRepositoryImpl implements FriendApplyRepository {
    private final FriendApplyMapper friendApplyMapper;

    @Override
    public void insert(FriendApply f) {
        friendApplyMapper.insert(f);
    }


    @Override
    public Optional<FriendApply> findFriendApply(FriendApply friendApply) {
        return Optional.empty();
    }
}
