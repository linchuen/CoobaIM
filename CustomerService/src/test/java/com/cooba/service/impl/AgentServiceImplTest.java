package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.dto.AgentInfo;
import com.cooba.entity.Agent;
import com.cooba.entity.AgentCustomer;
import com.cooba.exception.BaseException;
import com.cooba.repository.AgentCustomerRepository;
import com.cooba.repository.AgentRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collections;
import java.util.List;

@MybatisLocalTest
@ContextConfiguration(classes = {AgentServiceImpl.class})
@Sql(scripts = {"/sql/Agent-schema.sql", "/sql/AgentCustomer-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class AgentServiceImplTest {
    @Autowired
    AgentServiceImpl agentService;

    @Autowired
    AgentRepository agentRepository;

    @Autowired
    AgentCustomerRepository agentCustomerRepository;

    @Test
    @DisplayName("建立客服")
    void create() {
        Agent agent = Instancio.create(Agent.class);
        agent.setId(null);
        Long agentId = agentService.create(agent);

        Assertions.assertNotNull(agentId);
    }

    @Test
    @DisplayName("更新客服")
    void update() {
        Agent agent = Instancio.create(Agent.class);
        Long agentId = agentService.create(agent);

        boolean disable = !agent.getIsDisable();
        agent.setIsDisable(disable);
        agentService.update(agent);

        Agent updateAgent = agentRepository.selectById(agentId);

        Assertions.assertEquals(updateAgent.getIsDisable(), disable);
    }

    @Test
    @DisplayName("更新不存在客服")
    void updateNotExist() {
        Agent agent = Instancio.create(Agent.class);
        agentService.create(agent);

        boolean disable = !agent.getIsDisable();
        agent.setId(0L);
        agent.setIsDisable(disable);

        Assertions.assertThrows(BaseException.class, () -> agentService.update(agent));
    }

    @Test
    @DisplayName("搜尋客服")
    void search() {
        long agentUserId = 1;
        Agent agent = Instancio.create(Agent.class);
        agent.setUserId(agentUserId);
        agentService.create(agent);

        Agent search = agentService.search(agentUserId);

        Assertions.assertEquals(agent.getId(), search.getId());
    }

    @Test
    @DisplayName("批次搜尋客服")
    void searchBatch() {
        agentService.create(Instancio.create(Agent.class));
        agentService.create(Instancio.create(Agent.class));
        List<AgentInfo> agentInfoList = agentService.search(Collections.emptyList());

        Assertions.assertEquals(2, agentInfoList.size());
    }

    @Test
    void disable() {
        Agent agent = Instancio.create(Agent.class);
        agent.setIsDisable(false);
        Long agentId = agentService.create(agent);

        agentService.disable(agent);

        Agent updateAgent = agentRepository.selectById(agentId);

        Assertions.assertEquals(true, updateAgent.getIsDisable());
    }

    @Test
    void bindCustomer() {
        AgentCustomer agentCustomer = Instancio.create(AgentCustomer.class);
        agentCustomer.setId(null);
        agentService.bindCustomer(List.of(agentCustomer));

        AgentCustomer select = agentCustomerRepository.selectById(agentCustomer.getId());

        Assertions.assertNotNull(select);
    }

    @Test
    void unbindCustomer() {
        AgentCustomer agentCustomer = Instancio.create(AgentCustomer.class);
        agentCustomer.setId(null);
        agentService.bindCustomer(List.of(agentCustomer));

        long agentUserId = agentCustomer.getAgentUserId();
        List<Long> customerUserIds = List.of(agentCustomer.getCustomerUserId());
        agentService.unbindCustomer(agentUserId, customerUserIds);

        AgentCustomer select = agentCustomerRepository.selectById(agentCustomer.getId());

        Assertions.assertNull(select);
    }
}