package com.cooba.repository;

import com.cooba.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends BaseRepository<Ticket> {

    Optional<Ticket> findLastTicketByCustomerId(long customerUserId);

    List<Ticket> findTicketsByAgentAndCustomer(long agentUserId, long customerUserId);

    List<Ticket> findTicketByByAgent(long agentUserId);
}
