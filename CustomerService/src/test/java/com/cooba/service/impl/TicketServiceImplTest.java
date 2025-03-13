package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.entity.FriendApply;
import com.cooba.entity.Ticket;
import com.cooba.service.TicketService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@MybatisLocalTest
@ContextConfiguration(classes = {TicketServiceImpl.class})
@Sql(scripts = {"/sql/Ticket-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class TicketServiceImplTest {
    @Autowired
    TicketService ticketService;

    @Test
    @DisplayName("建立工單")
    void create() {
        Ticket ticket = Instancio.create(Ticket.class);
        Long id = ticketService.create(ticket);

        Assertions.assertNotNull(id);
    }

    @Test
    void findLastTicket() {
        long customerId = 1;
        Ticket ticket1 = Instancio.create(Ticket.class);
        ticket1.setId(null);
        ticket1.setCustomerUserId(customerId);
        Long id1 = ticketService.create(ticket1);

        Ticket ticket2 = Instancio.create(Ticket.class);
        ticket2.setId(null);
        ticket2.setCustomerUserId(customerId);
        Long id2 = ticketService.create(ticket2);

        Assertions.assertTrue(id2 > id1);

        Optional<Ticket> lastTicket = ticketService.findLastTicket(customerId);
        Assertions.assertTrue(lastTicket.isPresent());
        Assertions.assertEquals(lastTicket.get().getId(), id2);
    }

    @Test
    void searchCustomerTicket() {
        long customerId = 1;
        Ticket ticket1 = Instancio.create(Ticket.class);
        ticket1.setId(null);
        ticket1.setAgentUserId(1L);
        ticket1.setCustomerUserId(customerId);
        Long id1 = ticketService.create(ticket1);

        Ticket ticket2 = Instancio.create(Ticket.class);
        ticket2.setId(null);
        ticket2.setAgentUserId(2L);
        ticket2.setCustomerUserId(customerId);
        Long id2 = ticketService.create(ticket2);


        List<Ticket> tickets = ticketService.searchCustomerTicket(2L, customerId);
        Assertions.assertEquals(tickets.get(0).getId(), id2);
    }

    @Test
    void searchAgentTicket() {
        long customerId = 1;
        Ticket ticket1 = Instancio.create(Ticket.class);
        ticket1.setId(null);
        ticket1.setAgentUserId(2L);
        ticket1.setCustomerUserId(customerId);
        Long id1 = ticketService.create(ticket1);

        Ticket ticket2 = Instancio.create(Ticket.class);
        ticket2.setId(null);
        ticket2.setAgentUserId(2L);
        ticket2.setCustomerUserId(customerId);
        Long id2 = ticketService.create(ticket2);


        List<Ticket> tickets = ticketService.searchAgentTicket(2L, 1);
        Assertions.assertEquals(tickets.size(), 1);
        Assertions.assertEquals(tickets.get(0).getId(), id2);
    }
}