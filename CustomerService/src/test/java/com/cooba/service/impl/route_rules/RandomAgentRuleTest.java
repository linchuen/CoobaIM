package com.cooba.service.impl.route_rules;

import com.cooba.entity.Agent;
import com.cooba.entity.User;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RandomAgentRule.class})
class RandomAgentRuleTest {
    @Autowired
    RandomAgentRule randomAgentRule;

    @Test
    void filterAgent() {
        List<Agent> agents = Instancio.createList(Agent.class);
        User customer = Instancio.create(User.class);

        Map<Long, Integer> agentCountMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            List<Agent> filterAgent = randomAgentRule.filterAgent(agents, customer);
            Assertions.assertEquals(1, filterAgent.size());

            agentCountMap.compute(filterAgent.get(0).getId(), (k, v) -> (v == null) ? 1 : v + 1);
        }
        agentCountMap.forEach((k, v) -> Assertions.assertTrue(v < 100));

    }
}