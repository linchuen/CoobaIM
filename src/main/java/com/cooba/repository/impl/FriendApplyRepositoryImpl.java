package com.cooba.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.DataManipulateLayer;
import com.cooba.constant.ErrorEnum;
import com.cooba.entity.FriendApply;
import com.cooba.exception.BaseException;
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
    public FriendApply selectById(long id) {
        return friendApplyMapper.selectById(id);
    }

    @Override
    public void deleteById(long id) {
        friendApplyMapper.deleteById(id);
    }


    @Override
    public List<FriendApply> findByPermitId(long permitId) {
        return friendApplyMapper.selectList(new LambdaQueryWrapper<FriendApply>()
                .eq(FriendApply::getPermitUserId, permitId));
    }

    @Override
    public Optional<FriendApply> findByApplyIdAndPermitId(FriendApply friendApply) {
        FriendApply apply = friendApplyMapper.selectOne(new LambdaQueryWrapper<FriendApply>()
                .eq(FriendApply::getApplyUserId, friendApply.getApplyUserId())
                .eq(FriendApply::getPermitUserId, friendApply.getPermitUserId())
                .or()
                .eq(FriendApply::getApplyUserId, friendApply.getPermitUserId())
                .eq(FriendApply::getPermitUserId, friendApply.getApplyUserId()));
        return Optional.ofNullable(apply);
    }

    @Override
    public void updateByApplyIdAndPermitId(FriendApply friendApply) {
        int update = friendApplyMapper.update(friendApply, new LambdaQueryWrapper<FriendApply>()
                .eq(FriendApply::getApplyUserId, friendApply.getApplyUserId())
                .eq(FriendApply::getPermitUserId, friendApply.getPermitUserId())
        );
        if (update == 0) throw new BaseException(ErrorEnum.FRIEND_APPLY_NOT_EXIST);
    }

    @Override
    public void deleteByApplyIdAndPermitId(FriendApply friendApply) {
        friendApplyMapper.delete(new LambdaQueryWrapper<FriendApply>()
                .eq(FriendApply::getApplyUserId, friendApply.getApplyUserId())
                .eq(FriendApply::getPermitUserId, friendApply.getPermitUserId()));
    }

    @Override
    public void deleteByAllApplyIdAndPermitId(FriendApply friendApply) {
        friendApplyMapper.delete(new LambdaQueryWrapper<FriendApply>()
                .eq(FriendApply::getApplyUserId, friendApply.getApplyUserId())
                .eq(FriendApply::getPermitUserId, friendApply.getPermitUserId())
                .or()
                .eq(FriendApply::getApplyUserId, friendApply.getPermitUserId())
                .eq(FriendApply::getPermitUserId, friendApply.getApplyUserId())
        );
    }
}
