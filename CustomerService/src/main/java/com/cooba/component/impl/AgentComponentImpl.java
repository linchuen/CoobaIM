package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.aop.UserThreadLocal;
import com.cooba.component.AgentComponent;
import com.cooba.constant.RoomRoleEnum;
import com.cooba.dto.CustomerInfo;
import com.cooba.dto.request.*;
import com.cooba.dto.response.*;
import com.cooba.entity.*;
import com.cooba.service.AgentService;
import com.cooba.service.RoomService;
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
    private final RoomService roomService;
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
    public void updateAgent(AgentUpdateRequest request) {
        Agent agent = agentService.search(request.getAgentUserId());
        agent.setDisable(request.getIsDisable());

        agentService.update(agent);
    }

    @Override
    public void disableAgent(AgentDisableRequest request) {
        Agent agent = agentService.search(request.getAgentUserId());

        agentService.disable(agent);
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
        List<CustomerInfo> customerInfoList = agentService.searchCustomer(agent.getUserId())
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
        User currentUser = userThreadLocal.getCurrentUser();
        Agent agent = agentService.search(currentUser.getId());
        Agent transferAgent = agentService.search(request.getTransferUserId());
        User transferUser = userService.getInfo(transferAgent.getUserId());

        RoomUser roomUser = new RoomUser();
        roomUser.setRoomId(request.getRoomId());
        roomUser.setUserId(transferAgent.getId());
        roomUser.setShowName(transferUser.getName());
        roomUser.setRoomRoleEnum(RoomRoleEnum.MASTER);
        roomService.addUser(roomUser);

        RoomUser removeRoomUser = new RoomUser();
        removeRoomUser.setRoomId(request.getRoomId());
        removeRoomUser.setUserId(agent.getId());
        removeRoomUser.setShowName(currentUser.getName());
        removeRoomUser.setRoomRoleEnum(RoomRoleEnum.MASTER);
        roomService.deleteUser(removeRoomUser);

        return TicketTransferResponse.builder()
                .roomId(request.getRoomId())
                .userId(transferAgent.getId())
                .showName(transferUser.getName())
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
