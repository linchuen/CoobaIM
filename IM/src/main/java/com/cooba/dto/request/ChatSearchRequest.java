package com.cooba.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatSearchRequest {
    @NotNull
    private Long roomId;

    @NotEmpty
    private String word;

}
