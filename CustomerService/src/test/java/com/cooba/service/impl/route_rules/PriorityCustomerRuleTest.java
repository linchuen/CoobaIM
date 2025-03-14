package com.cooba.service.impl.route_rules;

import com.cooba.entity.Agent;
import com.cooba.entity.AgentCustomer;
import com.cooba.entity.User;
import com.cooba.repository.AgentCustomerRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PriorityCustomerRule.class})
class PriorityCustomerRuleTest {
    @Autowired
    PriorityCustomerRule priorityCustomerRule;
    @MockitoBean
    AgentCustomerRepository agentCustomerRepository;

    @Test
    void filterAgent() {
        List<Agent> agents = Instancio.createList(Agent.class);
        User customer = Instancio.create(User.class);

        AgentCustomer agentCustomer = Instancio.create(AgentCustomer.class);
        agentCustomer.setAgentUserId(agents.get(0).getUserId());
        agentCustomer.setCustomerUserId(customer.getId());
        Mockito.when(agentCustomerRepository.findByCustomerId(customer.getId())).thenReturn(List.of(agentCustomer));

        List<Agent> filterAgent = priorityCustomerRule.filterAgent(agents, customer);
        Assertions.assertFalse(filterAgent.isEmpty());
        Assertions.assertEquals(agents.get(0).getUserId(), filterAgent.get(0).getUserId());
    }
}