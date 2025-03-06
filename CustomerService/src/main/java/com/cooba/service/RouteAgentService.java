package com.cooba.service;

import java.util.List;

public abstract class RouteAgentService {
    private final List<RouteRule> routeRuleList;

    protected RouteAgentService(List<RouteRule> routeRuleList) {
        this.routeRuleList = routeRuleList;
    }

    public abstract void findSuitableAgent();

}
