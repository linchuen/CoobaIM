package com.cooba.service;

import com.cooba.entity.Ticket;

import java.util.List;

public interface TicketService {
    long create(Ticket ticket);

    Ticket findLastTicket(long customerUserId);

    List<Ticket> searchCustomerTicket(long agentUserId, long customerUserId);
}
