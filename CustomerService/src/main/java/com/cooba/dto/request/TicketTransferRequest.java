package com.cooba.dto.request;

import lombok.Data;

@Data
public class TicketTransferRequest {
    private final Long transferUserId;
    private final Long roomId;
}
