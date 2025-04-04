package com.cooba.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatLoadRequest {
    @NotNull
    private Long roomId;

    private Long chatId;

    private boolean searchAfter = false;
}
