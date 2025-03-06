package com.cooba.service;

import com.cooba.entity.Ticket;

public interface TicketService {
    Ticket create();

    Ticket findLastTicket();
}
