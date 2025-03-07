package com.cooba.service;

import com.cooba.entity.Agent;
import com.cooba.entity.User;

import java.util.List;

public abstract class RouteAgentService {
    private final List<RouteRule> routeRuleList;

    protected RouteAgentService(List<RouteRule> routeRuleList) {
        this.routeRuleList = routeRuleList;
    }

    public abstract Agent findSuitableAgent();

    public abstract User redirectAgent(long agentUserId);
}
