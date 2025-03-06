package com.cooba.controller;

import com.cooba.component.CustomerComponent;
import com.cooba.dto.request.RegisterRequest;
import com.cooba.dto.response.RegisterResponse;
import com.cooba.dto.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
