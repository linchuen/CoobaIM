package com.cooba.dto.response;

import com.cooba.entity.Ticket;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerEnterResponse {
    private Ticket ticket;
}
