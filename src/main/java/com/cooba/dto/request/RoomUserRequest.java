package com.cooba.dto.request;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class RoomUserRequest {
    @NotNull
    private Long roomId;

    @NotNull
    private Long userId;
}
