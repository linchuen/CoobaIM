package com.cooba.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatLoadRequest {
    @NotNull
    private Long roomId;

    @JsonDeserialize( using = StringToLongDeserializer.class)
    private Long chatId;

    private boolean searchAfter = false;
}
