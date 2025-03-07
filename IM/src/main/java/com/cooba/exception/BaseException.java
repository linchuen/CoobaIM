package com.cooba.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private IMError error;
    public BaseException() {
        super();
    }
    public BaseException(IMError error) {
        this.error = error;
    }
}
