package com.cooba.exception;

import static com.cooba.constant.ErrorEnum.JWT_TOKEN_INVALID;

public class JwtValidException extends BaseException{
    public JwtValidException() {
        super(JWT_TOKEN_INVALID);
    }
}
