package com.cooba.controller;

import com.cooba.component.CustomerComponent;
import com.cooba.dto.request.CustomerDetailRequest;
import com.cooba.dto.request.CustomerEnterRequest;
import com.cooba.dto.request.RegisterRequest;
import com.cooba.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Tag(name = "/customer", description = "用戶管理")
public class CustomerController {
    private final CustomerComponent customerComponent;

    @PostMapping("/create")
    @Operation(summary = "用戶建立")
    public ResultResponse<?> createUser(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = customerComponent.create(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/enter")
    @Operation(summary = "進入客服")
    public ResultResponse<?> enterRoom(@Valid @RequestBody CustomerEnterRequest request) {
        CustomerEnterResponse response = customerComponent.enterRoom(request);
        return ResultResponse.builder().data(response).build();
    }

    @GetMapping("/agent/search")
    @Operation(summary = "搜尋專屬客服")
    public ResultResponse<?> searchAgent() {
        CustomerAgentSearchResponse response = customerComponent.searchAgent();
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/guest/{id}")
    @Operation(summary = "遊客建立")
    public ResultResponse<?> createGuest(@PathVariable int id) {
        customerComponent.createGuest(id);
        return ResultResponse.builder().data(true).build();
    }

    @GetMapping("/guest")
    @Operation(summary = "取得遊客token")
    public ResultResponse<?> getGuestToken() {
        LoginResponse response = customerComponent.getGuestToken();
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/detail")
    @Operation(summary = "取得用戶詳細訊息")
    public ResultResponse<?> getGuestToken(@Valid @RequestBody CustomerDetailRequest request) {
        CustomerDetailResponse response = customerComponent.getDetails(request);
        return ResultResponse.builder().data(response).build();
    }
}
