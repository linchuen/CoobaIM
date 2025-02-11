package com.cooba.service;

import com.cooba.entity.Friend;
import com.cooba.entity.FriendApply;

import java.util.List;

public interface FriendService {

    long apply(FriendApply friendApply);

    void bind(FriendApply friendApply);

    void unbind(FriendApply friendApply);

    List<Friend> search(long userId, List<Long> friendUserIds);

    boolean isFriend(Friend friend);
}
