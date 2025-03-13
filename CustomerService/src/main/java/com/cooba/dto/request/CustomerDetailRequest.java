package com.cooba.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CustomerDetailRequest {
    private List<Long> userIds;
}
