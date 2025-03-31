package com.cooba.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ChatLoadDateRequest {
    @NotNull
    private Long roomId;

    private LocalDate date;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
