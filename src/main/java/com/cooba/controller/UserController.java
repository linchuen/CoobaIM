package com.cooba.controller;

import com.cooba.component.UserComponent;
import com.cooba.dto.request.RegisterRequest;
import com.cooba.dto.request.RoomUserRequest;
import com.cooba.dto.request.SessionRequest;
import com.cooba.dto.response.ResultResponse;
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
    public ResultResponse<?> registerUser(@RequestBody RegisterRequest request) {
        userComponent.register(request);
        return ResultResponse.builder().build();
    }

    @PostMapping("/login")
    public ResultResponse<?> login(@RequestBody SessionRequest request) {
        userComponent.login(request);
        return ResultResponse.builder().build();
    }

    @PostMapping("/logout")
    public ResultResponse<?> logout(@RequestBody SessionRequest request) {
        userComponent.logout(request);
        return ResultResponse.builder().build();
    }

    @PostMapping("/enter")
    public ResultResponse<?> enterRoom(@RequestBody RoomUserRequest request) {
        userComponent.enterRoom(request);
        return ResultResponse.builder().build();
    }

    @PostMapping("/leave")
    public ResultResponse<?> leaveRoom(@RequestBody RoomUserRequest request) {
        userComponent.leaveRoom(request);
        return ResultResponse.builder().build();
    }
}
