package com.cooba.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FriendRequest {
    @NotNull
    private Long applyUserId;
    @NotNull
    private Long permitUserId;
    private boolean isPermit = false;
}
