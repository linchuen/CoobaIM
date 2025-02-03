package com.cooba.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class FriendRequest {
    @NotNull
    @Schema(description = "申請用戶ID", example = "1")
    private Long applyUserId;

    @NotNull
    @Schema(description = "批准用戶ID", example = "2")
    private Long permitUserId;

    @Schema(description = "是否允許", example = "true")
    private Boolean isPermit;
}
