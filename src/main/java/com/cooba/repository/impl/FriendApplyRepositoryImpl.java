package com.cooba.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.FriendApply;
import com.cooba.mapper.FriendApplyMapper;
import com.cooba.repository.FriendApplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;


@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
public class FriendApplyRepositoryImpl implements FriendApplyRepository {
    private final FriendApplyMapper friendApplyMapper;

    @Override
    public void insert(FriendApply friendApply) {
        friendApplyMapper.insert(friendApply);
    }

    @Override
    public void insert(List<FriendApply> friendApples) {
        friendApplyMapper.insert(friendApples);
    }


    @Override
    public Optional<FriendApply> findFriendApply(FriendApply friendApply) {
        FriendApply apply = friendApplyMapper.selectOne(new LambdaQueryWrapper<FriendApply>()
                .eq(FriendApply::getApplyUserId, friendApply.getApplyUserId())
                .eq(FriendApply::getPermitUserId, friendApply.getPermitUserId())
                .or()
                .eq(FriendApply::getApplyUserId, friendApply.getPermitUserId())
                .eq(FriendApply::getPermitUserId, friendApply.getApplyUserId()));
        return Optional.ofNullable(apply);
    }
}
