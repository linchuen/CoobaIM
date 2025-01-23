package com.cooba.service;

import com.cooba.entity.Friend;

public interface FriendService {

    void apply(Friend friend);

    void bind(Friend friend);

    void unbind(Friend friend);
}
