package com.cooba.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ChatLoadLastAndUnReadRequest {
    @NotEmpty
    private List<Long> roomIds;
}
