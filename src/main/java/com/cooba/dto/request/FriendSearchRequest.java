package com.cooba.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class FriendSearchRequest {
    @NotNull
    @Schema(description = "搜尋用戶ID", example = "1")
    private List<Long> friendUserIds;
}
