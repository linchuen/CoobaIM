package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.aop.UserThreadLocal;
import com.cooba.component.AgentComponent;
import com.cooba.constatnt.CsErrorEnum;
import com.cooba.dto.CustomerInfo;
import com.cooba.dto.request.*;
import com.cooba.dto.response.*;
import com.cooba.entity.Agent;
import com.cooba.entity.AgentCustomer;
import com.cooba.entity.Ticket;
import com.cooba.entity.User;
import com.cooba.exception.BaseException;
import com.cooba.service.AgentService;
import com.cooba.service.TicketService;
import com.cooba.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
@ObjectLayer
@RequiredArgsConstructor
public class AgentComponentImpl implements AgentComponent {
    private final UserService userService;
    private final AgentService agentService;
    private final TicketService ticketService;
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
        Agent agent = agentService.search(request.getAgentUserId());
        agent.setDisable(request.getIsDisable());

        agentService.update(agent);
        return AgentUpdateResponse.builder()
                .build();
    }

    @Override
    public AgentDisableResponse disableAgent(AgentDisableRequest request) {
        long userId = userThreadLocal.getCurrentUserId();
        Agent agent = agentService.search(userId);

        agentService.disable(agent);

        return AgentDisableResponse.builder()
                .build();
    }

    @Override
    public AgentSearchResponse searchAgent(AgentSearchRequest request) {
        List<Agent> agents = agentService.search(request.getAgentIds());
        return AgentSearchResponse.builder()
                .agents(agents)
                .build();
    }

    @Override
    public CustomerSearchResponse searchCustomer() {
        long userId = userThreadLocal.getCurrentUserId();
        Agent agent = agentService.search(userId);
        List<CustomerInfo> customerInfoList = agentService.searchCustomer(agent.getId())
                .stream()
                .map(CustomerInfo::new)
                .toList();

        return CustomerSearchResponse.builder()
                .customerInfos(customerInfoList)
                .build();
    }

    @Override
    public CustomerTicketSearchResponse searchCustomerTicket(CustomerTicketSearchRequest request) {
        long userId = userThreadLocal.getCurrentUserId();
        Agent agent = agentService.search(userId);

        List<Ticket> tickets = ticketService.searchCustomerTicket(agent.getUserId(), request.getCustomerUserId())
                .stream()
                .filter(Ticket::isOpen)
                .toList();
        return CustomerTicketSearchResponse.builder()
                .tickets(tickets)
                .build();
    }

    @Override
    public TicketTransferResponse transferTicket(TicketTransferRequest request) {
        return TicketTransferResponse.builder()
                .build();
    }

    @Override
    public void bindCustomer(AgentCustomerRequest request) {
        long userId = userThreadLocal.getCurrentUserId();
        Agent agent = agentService.search(userId);

        List<Long> customerUserIds = request.getUserIds();

        List<User> users = userService.getInfoList(customerUserIds);

        List<AgentCustomer> agentCustomers = users.stream()
                .map(user -> {
                    AgentCustomer agentCustomer = new AgentCustomer();
                    agentCustomer.setAgentId(agent.getId());
                    agentCustomer.setAgentUserId(agent.getUserId());
                    agentCustomer.setCustomerUserId(user.getId());
                    agentCustomer.setShowName(user.getName());
                    return agentCustomer;
                })
                .toList();
        agentService.bindCustomer(agentCustomers);
    }

    @Override
    public void unbindCustomer(AgentCustomerRequest request) {
        long userId = userThreadLocal.getCurrentUserId();
        Agent agent = agentService.search(userId);

        agentService.unbindCustomer(agent.getUserId(), request.getUserIds());
    }
}
