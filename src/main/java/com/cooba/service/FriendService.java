package com.cooba.service;

import com.cooba.dto.FriendApplyInfo;
import com.cooba.entity.Friend;
import com.cooba.entity.FriendApply;

import java.util.List;

public interface FriendService {

    long apply(FriendApply friendApply);

    void bind(FriendApply friendApply);

    void unbind(FriendApply friendApply);

    void tagRoom(List<Long> userIds, long roomId);

    List<Friend> search(long userId, List<Long> friendUserIds);

    List<FriendApplyInfo> searchApply(long userId);

    boolean isFriend(Friend friend);
}
