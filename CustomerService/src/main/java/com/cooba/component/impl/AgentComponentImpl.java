package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.aop.UserThreadLocal;
import com.cooba.component.AgentComponent;
import com.cooba.constant.ErrorEnum;
import com.cooba.dto.request.AgentCreateRequest;
import com.cooba.dto.request.AgentDisableRequest;
import com.cooba.dto.request.AgentSearchRequest;
import com.cooba.dto.request.AgentUpdateRequest;
import com.cooba.dto.response.AgentCreateResponse;
import com.cooba.dto.response.AgentDisableResponse;
import com.cooba.dto.response.AgentSearchResponse;
import com.cooba.dto.response.AgentUpdateResponse;
import com.cooba.entity.Agent;
import com.cooba.entity.User;
import com.cooba.exception.BaseException;
import com.cooba.service.AgentService;
import com.cooba.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ObjectLayer
@RequiredArgsConstructor
public class AgentComponentImpl implements AgentComponent {
    private final UserService userService;
    private final AgentService agentService;
    private final UserThreadLocal userThreadLocal;

    @Override
    public AgentCreateResponse createAgent(AgentCreateRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        long userId = userService.register(user);

        Agent agent = new Agent();
        agent.setUserId(userId);
        long agentId = agentService.create(agent);
        return AgentCreateResponse.builder()
                .agentId(agentId)
                .userId(userId)
                .isDisable(agent.isDisable())
                .createdTime(agent.getCreatedTime())
                .build();
    }

    @Override
    public AgentUpdateResponse updateAgent(AgentUpdateRequest request) {
        long userId = userThreadLocal.getCurrentUserId();
        return null;
    }

    @Override
    public AgentDisableResponse disableAgent(AgentDisableRequest request) {
        long userId = userThreadLocal.getCurrentUserId();
        Agent agent = agentService.search(userId).orElseThrow(() -> new BaseException(ErrorEnum.INVALID_AUTHORIZATION));
        agent.setDisable(true);
        agentService.disable(agent);

        return AgentDisableResponse.builder()
                .build();
    }

    @Override
    public AgentSearchResponse searchAgent(AgentSearchRequest request) {

        return null;
    }

    @Override
    public void searchCustomer() {

    }

    @Override
    public void searchCustomerTicket() {

    }

    @Override
    public void transferTicket() {

    }

    @Override
    public void manageCustomer() {

    }
}
