package com.cooba.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class AgentCustomerRequest {
    private final List<Long> userIds;
}
