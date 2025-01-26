package com.cooba.service;

import com.cooba.entity.FriendApply;

public interface FriendService {

    void apply(FriendApply friendApply);

    void bind(FriendApply friendApply);

    void unbind(FriendApply friendApply);
}
