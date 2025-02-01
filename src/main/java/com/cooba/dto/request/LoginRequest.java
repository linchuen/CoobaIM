package com.cooba.dto.request;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
public class LoginRequest {
    @NotNull
    private Long userId;

    @NotEmpty
    private String password;
}
