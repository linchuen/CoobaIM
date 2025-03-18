package com.cooba.dto.request;

import lombok.Data;

@Data
public class ChatIsReadRequest {
    private Long roomId;
    private Long chatId;
}
