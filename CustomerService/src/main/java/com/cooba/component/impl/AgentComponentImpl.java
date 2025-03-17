package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.aop.UserThreadLocal;
import com.cooba.component.AgentComponent;
import com.cooba.constant.EventEnum;
import com.cooba.constant.RoleEnum;
import com.cooba.constant.RoomRoleEnum;
import com.cooba.constant.RoomTypeEnum;
import com.cooba.core.SocketConnection;
import com.cooba.dto.AgentInfo;
import com.cooba.dto.CustomerInfo;
import com.cooba.dto.FriendBindResult;
import com.cooba.dto.request.*;
import com.cooba.dto.response.*;
import com.cooba.entity.*;
import com.cooba.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Slf4j
@ObjectLayer
@RequiredArgsConstructor
public class AgentComponentImpl implements AgentComponent {
    private final UserService userService;
    private final AgentService agentService;
    private final FriendService friendService;
    private final RoomService roomService;
    private final TicketService ticketService;
    private final SocketConnection socketConnection;
    private final UserThreadLocal userThreadLocal;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AgentCreateResponse createAgent(AgentCreateRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(RoleEnum.AGENT.getRole());

        long userId = userService.register(user);

        List<AgentInfo> currentAgents = agentService.search(Collections.emptyList());

        Agent agent = new Agent();
        agent.setUserId(userId);
        agent.setName(request.getName());
        agent.setDepartment(request.getDepartment());
        long agentId = agentService.create(agent);

        currentAgents
                .stream()
                .filter(agentInfo -> !agentInfo.getIsDisable())
                .forEach(agentInfo -> {
                    FriendApply friendApply = new FriendApply();
                    friendApply.setApplyUserId(agent.getUserId());
                    friendApply.setPermitUserId(agentInfo.getUserId());

                    Room room = new Room();
                    room.setName(UUID.randomUUID().toString());
                    room.setRoomTypeEnum(RoomTypeEnum.PERSONAL);
                    roomService.build(room, userId, List.of(agentInfo.getUserId()));
                    FriendBindResult friendBindResult = friendService.bindDirectly(friendApply, () -> room);

                    socketConnection.sendUserEvent(String.valueOf(friendApply.getApplyUserId()), EventEnum.FRIEND_ADD, friendBindResult.getApplyUser());
                    socketConnection.sendUserEvent(String.valueOf(friendApply.getPermitUserId()), EventEnum.FRIEND_ADD, friendBindResult.getPermitUser());
                });

        return AgentCreateResponse.builder()
                .agentId(agentId)
                .userId(userId)
                .isDisable(agent.getIsDisable())
                .createdTime(agent.getCreatedTime())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAgent(AgentUpdateRequest request) {
        Agent agent = agentService.search(request.getAgentUserId());
        agent.setIsDisable(request.getIsDisable());

        agentService.update(agent);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disableAgent(AgentDisableRequest request) {
        Agent agent = new Agent();
        agent.setUserId(request.getAgentUserId());
        agentService.disable(agent);
    }

    @Override
    public AgentSearchResponse searchAgent(AgentSearchRequest request) {
        List<AgentInfo> agents = agentService.search(request.getAgentIds());
        return AgentSearchResponse.builder()
                .agents(agents)
                .build();
    }

    @Override
    public CustomerSearchResponse searchBindCustomer(BindCustomerSearchRequest request) {
        long userId = userThreadLocal.getCurrentUserId();
        Agent agent = agentService.search(request.getAgentUserId() == null ? userId : request.getAgentUserId());
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
                .filter(Ticket::getIsOpen)
                .toList();
        return CustomerTicketSearchResponse.builder()
                .tickets(tickets)
                .build();
    }

    @Override
    public TicketSearchResponse searchRecentTicket() {
        long userId = userThreadLocal.getCurrentUserId();
        Agent agent = agentService.search(userId);

        List<Ticket> tickets = ticketService.searchAgentTicket(agent.getUserId(), 10)
                .stream()
                .toList();
        return TicketSearchResponse.builder()
                .tickets(tickets)
                .build();
    }

    @Override
    public TicketSearchResponse searchTicket(TicketSearchRequest request) {
        long userId = userThreadLocal.getCurrentUserId();
        Agent agent = agentService.search(userId);

        List<Ticket> tickets = ticketService.searchAgentTicket(agent.getUserId(), 20)
                .stream()
                .toList();
        return TicketSearchResponse.builder()
                .tickets(tickets)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    public CustomerBindResponse bindCustomer(AgentCustomerRequest request) {
        long userId = userThreadLocal.getCurrentUserId();
        Agent agent = agentService.search(userId);

        List<Long> customerUserIds = request.getUserIds();

        List<User> users = userService.getInfoList(customerUserIds);

        List<AgentCustomer> agentCustomers = users.stream()
                .map(user -> {
                    Room room = new Room();
                    room.setName(UUID.randomUUID().toString());
                    roomService.build(room, List.of(agent.getUserId(), user.getId()));

                    AgentCustomer agentCustomer = new AgentCustomer();
                    agentCustomer.setAgentUserId(agent.getUserId());
                    agentCustomer.setCustomerUserId(user.getId());
                    agentCustomer.setShowName(user.getName());
                    agentCustomer.setRoomId(room.getId());
                    return agentCustomer;
                })
                .toList();
        agentService.bindCustomer(agentCustomers);

        return CustomerBindResponse.builder()
                .agentCustomers(agentCustomers)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unbindCustomer(AgentCustomerRequest request) {
        long userId = userThreadLocal.getCurrentUserId();
        Agent agent = agentService.search(userId);

        agentService.unbindCustomer(agent.getUserId(), request.getUserIds());
    }
}
