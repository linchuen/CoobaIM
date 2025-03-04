package com.cooba.dto.request;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class LogoutRequest {
    @NotNull
    private Long userId;
}
