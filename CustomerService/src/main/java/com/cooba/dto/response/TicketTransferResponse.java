package com.cooba.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TicketTransferResponse {
    private Long roomId;
    private Long userId;
    private String showName;
}
