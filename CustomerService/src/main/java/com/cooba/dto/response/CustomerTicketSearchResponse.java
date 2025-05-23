package com.cooba.dto.response;

import com.cooba.entity.Ticket;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CustomerTicketSearchResponse {
    private List<Ticket> tickets;
}
