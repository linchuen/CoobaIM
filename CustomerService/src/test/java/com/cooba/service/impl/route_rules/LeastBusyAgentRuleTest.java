package com.cooba.service.impl.route_rules;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.entity.Agent;
import com.cooba.entity.Ticket;
import com.cooba.entity.User;
import com.cooba.repository.TicketRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@MybatisLocalTest
@ContextConfiguration(classes = {LeastBusyAgentRule.class})
@Sql(scripts = {"/sql/Ticket-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class LeastBusyAgentRuleTest {
    @Autowired
    LeastBusyAgentRule leastBusyAgentRule;
    @Autowired
    TicketRepository ticketRepository;

    @Test
    void filterAgent() {
        List<Agent> agents = Instancio.createList(Agent.class);
        User customer = Instancio.create(User.class);

        for (int i = 1; i < agents.size(); i++) {
            Agent agent = agents.get(i);

            List<Ticket> tickets = Instancio.of(Ticket.class)
                    .stream()
                    .limit(10)
                    .peek(ticket -> {
                        ticket.setAgentUserId(agent.getUserId());
                        ticket.setIsOpen(true);
                    })
                    .toList();
            ticketRepository.insert(tickets);
        }

        List<Agent> filterAgent = leastBusyAgentRule.filterAgent(agents, customer);
        Assertions.assertEquals(agents.get(0).getUserId(), filterAgent.get(0).getUserId());
        Assertions.assertEquals(1, filterAgent.size());
    }
}