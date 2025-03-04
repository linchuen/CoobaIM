package com.cooba.exception;

import com.cooba.constant.ErrorEnum;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private ErrorEnum errorEnum;
    public BaseException() {
        super();
    }
    public BaseException(ErrorEnum errorEnum) {
        this.errorEnum = errorEnum;
    }
}
