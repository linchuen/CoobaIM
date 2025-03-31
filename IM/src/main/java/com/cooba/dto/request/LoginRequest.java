package com.cooba.dto.request;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
public class LoginRequest {
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String platform;

    private String ip;
}
