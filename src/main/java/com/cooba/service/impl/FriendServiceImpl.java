package com.cooba.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.BehaviorLayer;
import com.cooba.constant.ErrorEnum;
import com.cooba.entity.Friend;
import com.cooba.entity.FriendApply;
import com.cooba.entity.User;
import com.cooba.exception.BaseException;
import com.cooba.repository.FriendApplyRepository;
import com.cooba.repository.FriendRepository;
import com.cooba.repository.UserRepository;
import com.cooba.service.FriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendApplyRepository friendApplyRepository;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long apply(FriendApply friendApply) {
        friendApplyRepository.findFriendApply(friendApply)
                .orElseThrow(() -> new BaseException(ErrorEnum.FRIEND_APPLY_EXIST));

        friendApplyRepository.insert(friendApply);
        return friendApply.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bind(FriendApply friendApply) {
        if (friendApply.isPermit()) {
            friendApply.setPermitTime(LocalDateTime.now());
            int update = friendApplyRepository.update(friendApply, new LambdaQueryWrapper<FriendApply>()
                    .eq(FriendApply::getApplyUserId, friendApply.getApplyUserId())
                    .eq(FriendApply::getPermitUserId, friendApply.getPermitUserId())
            );
            if (update == 0) throw new BaseException(ErrorEnum.FRIEND_APPLY_NOT_EXIST);

            User permitUser = userRepository.selectById(friendApply.getApplyUserId());
            Friend apply = new Friend();
            apply.setUserId(friendApply.getApplyUserId());
            apply.setFriendUserId(friendApply.getPermitUserId());
            apply.setShowName(permitUser.getName());
            friendRepository.insert(apply);

            User applyUser = userRepository.selectById(friendApply.getApplyUserId());
            Friend permit = new Friend();
            permit.setUserId(friendApply.getPermitUserId());
            permit.setFriendUserId(friendApply.getApplyUserId());
            permit.setShowName(applyUser.getName());
            friendRepository.insert(permit);
        } else {
            friendApplyRepository.delete(new LambdaQueryWrapper<FriendApply>()
                    .eq(FriendApply::getApplyUserId, friendApply.getApplyUserId())
                    .eq(FriendApply::getPermitUserId, friendApply.getPermitUserId())
            );
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unbind(FriendApply friendApply) {
        friendApplyRepository.delete(new LambdaQueryWrapper<FriendApply>()
                .eq(FriendApply::getApplyUserId, friendApply.getApplyUserId())
                .eq(FriendApply::getPermitUserId, friendApply.getPermitUserId())
                .or()
                .eq(FriendApply::getApplyUserId, friendApply.getPermitUserId())
                .eq(FriendApply::getPermitUserId, friendApply.getApplyUserId())
        );

        friendRepository.delete(new LambdaQueryWrapper<Friend>()
                .eq(Friend::getUserId, friendApply.getApplyUserId())
                .eq(Friend::getFriendUserId, friendApply.getPermitUserId()));

        friendRepository.delete(new LambdaQueryWrapper<Friend>()
                .eq(Friend::getUserId, friendApply.getPermitUserId())
                .eq(Friend::getFriendUserId, friendApply.getApplyUserId()));
    }

    @Override
    public void tagRoom(List<Long> userIds, long roomId) {
        Friend friend = new Friend();
        friend.setRoomId(roomId);
        friendRepository.update(friend, new LambdaQueryWrapper<Friend>()
                .in(Friend::getUserId, userIds));
    }

    @Override
    public List<Friend> search(long userId, List<Long> friendUserIds) {
        if (friendUserIds.isEmpty()) {
            return friendRepository.selectList(new LambdaQueryWrapper<Friend>()
                    .eq(Friend::getUserId, userId));
        }

        return friendRepository.selectList(new LambdaQueryWrapper<Friend>()
                .eq(Friend::getUserId, userId)
                .in(Friend::getFriendUserId, friendUserIds));
    }

    @Override
    public boolean isFriend(Friend friend) {
        return friendRepository.selectOne(new LambdaQueryWrapper<Friend>()
                .eq(Friend::getUserId, friend.getUserId())
                .eq(Friend::getFriendUserId, friend.getFriendUserId())) != null;
    }
}
