package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.constant.ErrorEnum;
import com.cooba.dto.FriendApplyInfo;
import com.cooba.dto.FriendBindResult;
import com.cooba.entity.Friend;
import com.cooba.entity.FriendApply;
import com.cooba.entity.Room;
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
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
        friendApplyRepository.findByApplyIdAndPermitId(friendApply)
                .ifPresent(apply -> {
                    throw new BaseException(ErrorEnum.FRIEND_APPLY_EXIST);
                });

        friendApplyRepository.insert(friendApply);
        return friendApply.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FriendBindResult bind(FriendApply friendApply, Supplier<Room> roomSupplier) {
        if (friendApply.isPermit()) {
            friendApply.setPermitTime(LocalDateTime.now());
            friendApplyRepository.updateByApplyIdAndPermitId(friendApply);

            return bindDirectly(friendApply,roomSupplier);
        } else {
            friendApplyRepository.deleteByApplyIdAndPermitId(friendApply);
            return FriendBindResult.builder()
                    .build();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unbind(FriendApply friendApply) {
        friendApplyRepository.deleteByAllApplyIdAndPermitId(friendApply);

        friendRepository.delete(friendApply.getApplyUserId(), friendApply.getPermitUserId());

        friendRepository.delete(friendApply.getPermitUserId(), friendApply.getApplyUserId());
    }

    @Override
    public FriendBindResult bindDirectly(FriendApply friendApply, Supplier<Room> roomSupplier) {
        Long roomId = roomSupplier.get().getId();
        User permitUser = userRepository.selectById(friendApply.getPermitUserId());
        Friend apply = new Friend();
        apply.setUserId(friendApply.getApplyUserId());
        apply.setFriendUserId(friendApply.getPermitUserId());
        apply.setShowName(permitUser.getName());
        apply.setRoomId(roomId);
        friendRepository.insert(apply);

        User applyUser = userRepository.selectById(friendApply.getApplyUserId());
        Friend permit = new Friend();
        permit.setUserId(friendApply.getPermitUserId());
        permit.setFriendUserId(friendApply.getApplyUserId());
        permit.setShowName(applyUser.getName());
        permit.setRoomId(roomId);
        friendRepository.insert(permit);

        return FriendBindResult.builder()
                .applyUser(apply)
                .permitUser(permit)
                .build();
    }

    @Override
    public List<Friend> search(long userId, List<Long> friendUserIds) {
        if (friendUserIds.isEmpty()) {
            return friendRepository.find(userId);
        }

        return friendRepository.find(userId).stream()
                .filter(friend -> friendUserIds.contains(friend.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<FriendApplyInfo> searchApply(long userId) {
        return friendApplyRepository.findByPermitId(userId)
                .stream()
                .filter(friendApply -> !friendApply.isPermit())
                .map(friendApply -> {
                    User applyUser = userRepository.selectById(friendApply.getApplyUserId());

                    FriendApplyInfo friendApplyInfo = new FriendApplyInfo();
                    friendApplyInfo.setId(friendApply.getId());
                    friendApplyInfo.setApplyId(applyUser.getId());
                    friendApplyInfo.setName(applyUser.getName());
                    return friendApplyInfo;
                })
                .toList();
    }

    @Override
    public boolean isFriend(Friend friend) {
        return friendRepository.find(friend.getUserId(), friend.getFriendUserId()).isPresent();
    }
}
