package com.cooba.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class FriendRemoveRequest {
    @NotNull
    @Schema(description = "刪除用戶ID", example = "1")
    private Long friendUserId;
}
