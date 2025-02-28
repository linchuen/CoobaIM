package com.cooba.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventEnum {
    FRIEND_APPLY("FRIEND_APPLY"),
    FRIEND_ADD("FRIEND_ADD"),
    ROOM_ADD("ROOM_ADD"),
    ;

    private final String type;
}
