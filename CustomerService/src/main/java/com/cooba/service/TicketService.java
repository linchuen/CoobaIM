package com.cooba.service;

import com.cooba.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    long create(Ticket ticket);

    Optional<Ticket> findLastTicket(long customerUserId);

    List<Ticket> searchCustomerTicket(long agentUserId, long customerUserId);

    List<Ticket> searchAgentTicket(long agentUserId, Integer limit);
}
