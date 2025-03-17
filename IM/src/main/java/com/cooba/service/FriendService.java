package com.cooba.service;

import com.cooba.dto.FriendApplyInfo;
import com.cooba.dto.FriendBindResult;
import com.cooba.entity.Friend;
import com.cooba.entity.FriendApply;
import com.cooba.entity.Room;

import java.util.List;
import java.util.function.Supplier;

public interface FriendService {

    long apply(FriendApply friendApply);

    FriendBindResult bind(FriendApply friendApply, Supplier<Room> roomSupplier);

    void unbind(FriendApply friendApply);

    FriendBindResult bindDirectly(FriendApply friendApply, Supplier<Room> roomSupplier);

    List<Friend> search(long userId, List<Long> friendUserIds);

    List<FriendApplyInfo> searchApply(long userId);

    boolean isFriend(Friend friend);
}
