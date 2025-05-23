package com.cooba.constant;

import com.cooba.exception.IMError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorEnum implements IMError {
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
    NETWORK_ERROR(1010,"network error"),
    CHAT_ERROR(1011,"chat message error"),
    INVALID_EMAIL_FORMAT(1012,"invalid email format"),
    INVALID_PASSWORD_FORMAT(1013,"invalid password format"),
    UNKNOWN_ERROR(9999,"unknown error"),
    ;

    private final int code;
    private final String message;
}
