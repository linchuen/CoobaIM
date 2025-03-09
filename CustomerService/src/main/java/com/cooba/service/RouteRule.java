package com.cooba.service;

import com.cooba.entity.Agent;
import com.cooba.entity.User;

import java.util.List;

public interface RouteRule {
    List<Agent> filterAgent(List<Agent> agents, User customer);

    boolean isOpen();

    int getOrder();
}
