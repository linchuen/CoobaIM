package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.aop.UserThreadLocal;
import com.cooba.component.CustomerComponent;
import com.cooba.constant.RoleEnum;
import com.cooba.dto.request.CustomerEnterRequest;
import com.cooba.dto.request.RegisterRequest;
import com.cooba.dto.response.CustomerEnterResponse;
import com.cooba.dto.response.LoginResponse;
import com.cooba.dto.response.RegisterResponse;
import com.cooba.entity.*;
import com.cooba.service.*;
import com.cooba.util.ConnectionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@ObjectLayer
@RequiredArgsConstructor
public class CustomerComponentImpl implements CustomerComponent {
    private final UserService userService;
    private final TicketService ticketService;
    private final RoomService roomService;
    private final MessageService messageService;
    private final SessionService sessionService;
    private final RouteAgentService routeAgentService;
    private final GuestService guestService;
    private final UserThreadLocal userThreadLocal;
    private final ConnectionManager connectionManager;

    @Override
    public RegisterResponse create(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        long userId = userService.register(user);
        return RegisterResponse.builder()
                .userId(userId)
                .build();
    }

    @Override
    public CustomerEnterResponse enterRoom(CustomerEnterRequest request) {
        User currentUser = userThreadLocal.getCurrentUser();

        boolean isGuest = currentUser.getRole().equals(RoleEnum.GUEST.getRole());

        if (isGuest || !request.isUsePreviousChat()) {
            return createNewTicket(currentUser);
        } else {
            Optional<Ticket> lastTicket = ticketService.findLastTicket(currentUser.getId());
            if (lastTicket.isEmpty()) {
                return createNewTicket(currentUser);
            }
            Ticket ticket = lastTicket.get();

            boolean isOnline = connectionManager.isUserOnline(String.valueOf(ticket.getAgentUserId()));
            if (!isOnline) {
                Agent suitableAgent = routeAgentService.findSuitableAgent(currentUser);
                User redirectAgentInfo = userService.getInfo(suitableAgent.getUserId());

                RoomUser roomUser = new RoomUser();
                roomUser.setRoomId(ticket.getRoomId());
                roomUser.setUserId(redirectAgentInfo.getId());
                roomUser.setShowName(redirectAgentInfo.getName());
                roomService.addUser(roomUser);
            }
            return CustomerEnterResponse.builder()
                    .ticket(ticket)
                    .build();
        }
    }

    private CustomerEnterResponse createNewTicket(User currentUser) {
        Agent suitableAgent = routeAgentService.findSuitableAgent(currentUser);

        Room room = new Room();
        room.setName(UUID.randomUUID().toString());
        roomService.build(room, List.of(suitableAgent.getUserId(), currentUser.getId()));

        Ticket ticket = new Ticket();
        ticket.setName(room.getName());
        ticket.setRoomId(room.getId());
        ticket.setAgentUserId(suitableAgent.getUserId());
        ticket.setCustomerUserId(currentUser.getId());
        ticketService.create(ticket);

        return CustomerEnterResponse.builder()
                .ticket(ticket)
                .build();
    }

    @Override
    public void createGuest(int number) {
        User user = new User();
        user.setName("guest_" + number);
        user.setEmail("guest_" + number + "@example.com");
        user.setPassword("example");

        long userId = userService.register(user);
        guestService.create(userId);
    }

    @Override
    public LoginResponse getGuestToken() {
        long guestId = guestService.getRandomGuest();
        User user = userService.getInfo(guestId);
        Session session = sessionService.add(user);

        return LoginResponse.builder()
                .name(user.getName())
                .userId(session.getUserId())
                .platform(session.getPlatform())
                .token(session.getToken())
                .loginTime(session.getLoginTime())
                .expireTime(session.getExpireTime())
                .build();
    }
}
