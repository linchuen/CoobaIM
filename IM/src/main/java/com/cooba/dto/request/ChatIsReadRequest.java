package com.cooba.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatIsReadRequest {
    @NotNull
    private Long roomId;

    @NotNull
    @JsonDeserialize( using = StringToLongDeserializer.class)
    private Long chatId;
}
