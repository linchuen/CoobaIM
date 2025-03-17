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

    @GetMapping("/ticket")
    @Operation(summary = "搜尋近期工單")
    public ResultResponse<?> searchRecentTicket() {
        TicketSearchResponse response = agentComponent.searchRecentTicket();
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/ticket/search")
    @Operation(summary = "搜尋工單")
    public ResultResponse<?> searchTicket(TicketSearchRequest request) {
        TicketSearchResponse response = agentComponent.searchTicket(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/ticket/transfer")
    @Operation(summary = "轉移工單")
    public ResultResponse<?> transferTicket(@Valid @RequestBody TicketTransferRequest request) {
        TicketTransferResponse response = agentComponent.transferTicket(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/customer/search")
    @Operation(summary = "搜尋客戶")
    public ResultResponse<?> searchBindCustomer(@Valid @RequestBody BindCustomerSearchRequest request) {
        CustomerSearchResponse response = agentComponent.searchBindCustomer(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/customer/detail/search")
    @Operation(summary = "搜尋客戶")
    public ResultResponse<?> searchBindCustomerDetail(@Valid @RequestBody BindCustomerSearchRequest request) {
        CustomerDetailResponse response = agentComponent.searchBindCustomerDetail(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/customer/ticket")
    @Operation(summary = "搜尋客戶工單")
    public ResultResponse<?> searchCustomerTicket(@Valid @RequestBody CustomerTicketSearchRequest request) {
        CustomerTicketSearchResponse response = agentComponent.searchCustomerTicket(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/customer/bind")
    @Operation(summary = "綁定客戶")
    public ResultResponse<?> bindCustomer(@Valid @RequestBody AgentCustomerRequest request) {
        CustomerBindResponse response = agentComponent.bindCustomer(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/customer/unbind")
    @Operation(summary = "解除綁定客戶")
    public ResultResponse<?> unbindCustomer(@Valid @RequestBody AgentCustomerRequest request) {
        agentComponent.unbindCustomer(request);
        return ResultResponse.builder().data(true).build();
    }


}
