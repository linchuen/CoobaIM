package com.cooba.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FriendRemoveRequest {
    @NotNull
    private Long friendUserId;
}
