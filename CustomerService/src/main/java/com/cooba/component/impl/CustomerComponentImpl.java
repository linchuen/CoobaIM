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
import com.cooba.entity.Session;
import com.cooba.entity.Ticket;
import com.cooba.entity.User;
import com.cooba.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ObjectLayer
@RequiredArgsConstructor
public class CustomerComponentImpl implements CustomerComponent {
    private final UserService userService;
    private final TicketService ticketService;
    private final SessionService sessionService;
    private final RouteAgentService routeAgentService;
    private final GuestService guestService;
    private final UserThreadLocal userThreadLocal;

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

        Ticket ticket;
        if (isGuest || request.isUsePreviousChat()) {
            ticket = ticketService.create();
        } else {
            ticket = ticketService.findLastTicket();
        }
        if (request.isNeedRoute()) {
            routeAgentService.findSuitableAgent();
        }


        return CustomerEnterResponse.builder()

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
