package com.cooba.service;

import com.cooba.entity.Ticket;

import java.util.List;

public interface TicketService {
    Ticket create();

    Ticket findLastTicket();

    List<Ticket> searchCustomerTicket(long agentUserId, long customerUserId);
}
