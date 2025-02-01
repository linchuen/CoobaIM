package com.cooba.dto.request;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class FriendRequest {
    @NotNull
    private Long applyUserId;
    @NotNull
    private Long permitUserId;
    private Boolean isPermit;
}
