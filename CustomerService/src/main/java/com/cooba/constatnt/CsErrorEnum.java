package com.cooba.constatnt;

import com.cooba.exception.IMError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum CsErrorEnum implements IMError {
    AGENT_NOT_EXIST(2001,"agent not exist"),
    ;
    private final int code;
    private final String message;
}
