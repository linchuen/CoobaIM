package com.cooba.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorEnum {
    INVALID_AUTHORIZATION(401,"invalid authorization"),
    JWT_TOKEN_INVALID(401,"jwt token invalid"),
    FORBIDDEN(403,"forbidden"),
    INVALID_REQUEST(1001,"invalid request"),
    BUSINESS_ERROR(1002,"business error"),
    ROOM_NOT_EXIST(1003,"room not exist"),
    ROOM_USER_NOT_EXIST(1004,"room user not exist"),
    FRIEND_APPLY_EXIST(1005,"friend apply exist"),
    FRIEND_APPLY_NOT_EXIST(1006,"friend apply not exist"),
    SESSION_NOT_EXIST(1007,"session not exist"),
    USER_NOT_EXIST(1008,"user not exist"),
    ROOM_USER_EMPTY(1009,"room user is empty"),
    UNKNOWN_ERROR(9999,"unknown error"),
    ;

    private final int code;
    private final String message;
}
