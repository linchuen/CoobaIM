package com.cooba.service;

import com.cooba.entity.Friend;
import com.cooba.entity.FriendApply;

public interface FriendService {

    long apply(FriendApply friendApply);

    void bind(FriendApply friendApply);

    void unbind(FriendApply friendApply);

    boolean isFriend(Friend friend);
}
