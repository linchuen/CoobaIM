package com.cooba.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorEnum {
    INVALID_REQUEST(1001,"invalid request"),
    BUSINESS_ERROR(1002,"business error");;

    private final int code;
    private final String message;
}
