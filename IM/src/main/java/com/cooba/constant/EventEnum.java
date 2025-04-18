package com.cooba.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventEnum implements IMEvent{
    FRIEND_APPLY("friend_apply"),
    FRIEND_ADD("friend_add"),
    ROOM_ADD("room_add"),
    LIVE_CALL("live_call"),
    ERROR("error"),
    ;

    private final String type;
}
