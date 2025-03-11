package com.cooba.service.impl;

import com.cooba.entity.Ticket;
import com.cooba.repository.TicketRepository;
import com.cooba.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    @Override
    public long create(Ticket ticket) {
        ticketRepository.insert(ticket);
        return ticket.getId();
    }

    @Override
    public Optional<Ticket> findLastTicket(long customerUserId) {
        return ticketRepository.findLastTicketByCustomerId(customerUserId);
    }

    @Override
    public List<Ticket> searchCustomerTicket(long agentUserId, long customerUserId) {
        return ticketRepository.findTicketsByAgentAndCustomer(agentUserId, customerUserId);
    }

    @Override
    public List<Ticket> searchAgentTicket(long agentUserId, Integer limit) {
        if (limit == null) {
            return ticketRepository.findTicketByByAgent(agentUserId);
        }
        return ticketRepository.findTicketByByAgent(agentUserId, limit);
    }
}
