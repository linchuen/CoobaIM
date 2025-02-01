package com.cooba.dto.request;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class FriendRemoveRequest {
    @NotNull
    private Long friendUserId;
}
