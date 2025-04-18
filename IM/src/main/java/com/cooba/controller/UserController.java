package com.cooba.controller;

import com.cooba.component.UserComponent;
import com.cooba.dto.request.*;
import com.cooba.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "/user", description = "用戶管理")
public class UserController {
    private final UserComponent userComponent;

    @PostMapping("/register")
    @Operation(summary = "用戶註冊")
    public ResultResponse<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = userComponent.register(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/login")
    @Operation(summary = "用戶登入")
    public ResultResponse<?> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
        String ip = httpServletRequest.getHeader("X-Real-IP");
        request.setIp(ip);

        LoginResponse response = userComponent.login(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/logout")
    @Operation(summary = "用戶登出")
    public ResultResponse<?> logout(@Valid @RequestBody LogoutRequest request) {
        LogoutResponse response = userComponent.logout(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/refresh")
    @Operation(summary = "token刷新")
    public ResultResponse<?> refresh(@Valid @RequestBody RefreshRequest request, HttpServletRequest httpServletRequest) {
        String ip = httpServletRequest.getHeader("X-Real-IP");
        request.setIp(ip);

        LoginResponse response = userComponent.refreshToken(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/enter")
    @Operation(summary = "用戶加入聊天室")
    public ResultResponse<?> enterRoom(@Valid @RequestBody RoomUserRequest request) {
        RoomResponse response = userComponent.enterRoom(request);
        return ResultResponse.builder().data(response).build();
    }

    @DeleteMapping("/leave")
    @Operation(summary = "用戶離開聊天室")
    public ResultResponse<?> leaveRoom(@Valid @RequestBody RoomUserRequest request) {
        RoomResponse response = userComponent.leaveRoom(request);
        return ResultResponse.builder().data(response).build();
    }

    @GetMapping("/detail")
    @Operation(summary = "用戶詳情")
    public ResultResponse<?> getDetail() {
        UserDetailResponse response = userComponent.getDetail();
        return ResultResponse.builder().data(response).build();
    }
}
