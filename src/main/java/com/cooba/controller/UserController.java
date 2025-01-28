package com.cooba.controller;

import com.cooba.component.UserComponent;
import com.cooba.dto.request.LoginRequest;
import com.cooba.dto.request.RegisterRequest;
import com.cooba.dto.request.RoomUserRequest;
import com.cooba.dto.request.LogoutRequest;
import com.cooba.dto.response.*;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserComponent userComponent;

    @PostMapping("/register")
    public ResultResponse<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = userComponent.register(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/login")
    public ResultResponse<?> login(@RequestBody LoginRequest request) {
        LoginResponse response = userComponent.login(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/logout")
    public ResultResponse<?> logout(@Valid @RequestBody LogoutRequest request) {
        LogoutResponse response = userComponent.logout(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/enter")
    public ResultResponse<?> enterRoom(@Valid @RequestBody RoomUserRequest request) {
        RoomResponse response = userComponent.enterRoom(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/leave")
    public ResultResponse<?> leaveRoom(@Valid @RequestBody RoomUserRequest request) {
        RoomResponse response = userComponent.leaveRoom(request);
        return ResultResponse.builder().data(response).build();
    }
}
