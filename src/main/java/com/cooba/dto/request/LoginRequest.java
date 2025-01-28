package com.cooba.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {
    @NotNull
    private Long userId;

    @NotEmpty
    private String password;
}
