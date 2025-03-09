package com.cooba.controller;

import com.cooba.component.AgentComponent;
import com.cooba.dto.request.*;
import com.cooba.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agent")
@RequiredArgsConstructor
@Tag(name = "/agent", description = "客服管理")
public class AgentController {
    private final AgentComponent agentComponent;
    @PostMapping("/create")
    @Operation(summary = "客服建立")
    public ResultResponse<?> createAgent(@Valid @RequestBody AgentCreateRequest request) {
        AgentCreateResponse response = agentComponent.createAgent(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/update")
    @Operation(summary = "更新客服")
    public ResultResponse<?> updateAgent(@Valid @RequestBody AgentUpdateRequest request) {
        agentComponent.updateAgent(request);
        return ResultResponse.builder().data(true).build();
    }

    @PostMapping("/disable")
    @Operation(summary = "停用客服")
    public ResultResponse<?> disableAgent(@Valid @RequestBody AgentDisableRequest request) {
        agentComponent.disableAgent(request);
        return ResultResponse.builder().data(true).build();
    }

    @PostMapping("/search")
    @Operation(summary = "搜尋客服")
    public ResultResponse<?> searchAgent(@Valid @RequestBody AgentSearchRequest request) {
        AgentSearchResponse response = agentComponent.searchAgent(request);
        return ResultResponse.builder().data(response).build();
    }

    @GetMapping("/customer/customer")
    @Operation(summary = "搜尋客戶")
    public ResultResponse<?> searchCustomer() {
        CustomerSearchResponse response = agentComponent.searchCustomer();
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/customer/ticket")
    @Operation(summary = "搜尋客戶票務")
    public ResultResponse<?> searchCustomerTicket(@Valid @RequestBody CustomerTicketSearchRequest request) {
        CustomerTicketSearchResponse response = agentComponent.searchCustomerTicket(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/ticket/transfer")
    @Operation(summary = "轉移票務")
    public ResultResponse<?> transferTicket(@Valid @RequestBody TicketTransferRequest request) {
        TicketTransferResponse response = agentComponent.transferTicket(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/customer/bind")
    @Operation(summary = "綁定客戶")
    public ResultResponse<?> bindCustomer(@Valid @RequestBody AgentCustomerRequest request) {
        agentComponent.bindCustomer(request);
        return ResultResponse.builder().data(true).build();
    }

    @PostMapping("/customer/unbind")
    @Operation(summary = "解除綁定客戶")
    public ResultResponse<?> unbindCustomer(@Valid @RequestBody AgentCustomerRequest request) {
        agentComponent.unbindCustomer(request);
        return ResultResponse.builder().data(true).build();
    }


}
