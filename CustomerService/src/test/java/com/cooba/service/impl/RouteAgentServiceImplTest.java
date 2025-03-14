package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.entity.Agent;
import com.cooba.entity.User;
import com.cooba.exception.BaseException;
import com.cooba.repository.AgentRepository;
import com.cooba.service.RouteAgentService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@MybatisLocalTest
@ContextConfiguration(classes = {RouteAgentServiceImpl.class})
@Sql(scripts = {"/sql/Agent-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class RouteAgentServiceImplTest {
    @Autowired
    RouteAgentService routeAgentService;

    @Autowired
    AgentRepository agentRepository;

    @Test
    void findSuitableAgent() {
    }

    @Test
    @DisplayName("合適客服存在")
    void suitableAgentExist() {
        User user = Instancio.create(User.class);

        List<Agent> defaultAgents = Instancio.createList(Agent.class)
                .stream()
                .peek(agent -> {
                    agent.setIsDefault(true);
                    agent.setIsDisable(false);
                }).toList();
        List<Agent> agents = Instancio.createList(Agent.class)
                .stream()
                .peek(agent -> {
                    agent.setIsDefault(false);
                    agent.setIsDisable(false);
                }).collect(Collectors.toList());
        agents.addAll(defaultAgents);
        agentRepository.insert(agents);

        List<Long> agentUserIds = agents.stream().map(Agent::getUserId).toList();
        for (int i = 0; i < 20; i++) {
            Agent suitableAgent = routeAgentService.findSuitableAgent(agents, user);

            Assertions.assertTrue(agentUserIds.contains(suitableAgent.getUserId()));
        }
    }

    @Test
    @DisplayName("合適客服不存在，使用預設客服")
    void suitableAgentNotExist() {
        User user = Instancio.create(User.class);

        List<Agent> defaultAgents = Instancio.createList(Agent.class)
                .stream()
                .peek(agent -> {
                    agent.setIsDefault(true);
                    agent.setIsDisable(false);
                }).toList();
        agentRepository.insert(defaultAgents);

        List<Long> defaultAgentsUserIds = defaultAgents.stream().map(Agent::getUserId).toList();
        for (int i = 0; i < 20; i++) {
            Agent suitableAgent = routeAgentService.findSuitableAgent(Collections.emptyList(), user);

            Assertions.assertTrue(defaultAgentsUserIds.contains(suitableAgent.getUserId()));
        }
    }

    @Test
    @DisplayName("合適客服不存在，預設客服不存在")
    void noSuitableAgent() {
        User user = Instancio.create(User.class);

        Assertions.assertThrows(BaseException.class, () -> routeAgentService.findSuitableAgent(Collections.emptyList(), user));
    }
}