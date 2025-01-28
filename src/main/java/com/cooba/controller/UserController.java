package com.cooba.controller;

import com.cooba.component.UserComponent;
import com.cooba.dto.request.RegisterRequest;
import com.cooba.dto.request.RoomUserRequest;
import com.cooba.dto.request.SessionRequest;
import com.cooba.dto.response.ResultResponse;
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
        userComponent.register(request);
        return ResultResponse.builder().data(request.getName()).build();
    }

    @PostMapping("/login")
    public ResultResponse<?> login(@Valid @RequestBody SessionRequest request) {
        userComponent.login(request);
        return ResultResponse.builder().build();
    }

    @PostMapping("/logout")
    public ResultResponse<?> logout(@Valid @RequestBody SessionRequest request) {
        userComponent.logout(request);
        return ResultResponse.builder().build();
    }

    @PostMapping("/enter")
    public ResultResponse<?> enterRoom(@Valid @RequestBody RoomUserRequest request) {
        userComponent.enterRoom(request);
        return ResultResponse.builder().build();
    }

    @PostMapping("/leave")
    public ResultResponse<?> leaveRoom(@Valid @RequestBody RoomUserRequest request) {
        userComponent.leaveRoom(request);
        return ResultResponse.builder().build();
    }
}
