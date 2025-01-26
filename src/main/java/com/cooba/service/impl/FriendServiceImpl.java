package com.cooba.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.BehaviorLayer;
import com.cooba.entity.Friend;
import com.cooba.entity.FriendApply;
import com.cooba.repository.FriendApplyRepository;
import com.cooba.repository.FriendRepository;
import com.cooba.service.FriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendApplyRepository friendApplyRepository;
    private final FriendRepository friendRepository;

    @Override
    public void apply(FriendApply friendApply) {
        friendApplyRepository.insert(friendApply);
    }

    @Override
    public void bind(FriendApply friendApply) {
        if (friendApply.isPermit()) {
            friendApplyRepository.updateById(friendApply);

            Friend apply = new Friend();
            apply.setId(friendApply.getApplyUserId());
            apply.setFriendUserId(friendApply.getPermitUserId());
            friendRepository.insert(apply);

            Friend permit = new Friend();
            permit.setId(friendApply.getPermitUserId());
            permit.setId(friendApply.getApplyUserId());
            friendRepository.insert(permit);
        } else {
            friendApplyRepository.deleteById(friendApply);
        }
    }

    @Override
    public void unbind(FriendApply friendApply) {
        friendRepository.delete(new LambdaQueryWrapper<Friend>()
                .eq(Friend::getUserId, friendApply.getApplyUserId())
                .eq(Friend::getFriendUserId, friendApply.getPermitUserId()));

        friendRepository.delete(new LambdaQueryWrapper<Friend>()
                .eq(Friend::getUserId, friendApply.getPermitUserId())
                .eq(Friend::getFriendUserId, friendApply.getApplyUserId()));
    }
}
