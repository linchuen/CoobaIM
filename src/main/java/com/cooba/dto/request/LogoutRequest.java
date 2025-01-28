package com.cooba.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LogoutRequest {
    @NotNull
    private Long userId;
}
