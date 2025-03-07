package com.cooba.dto.response;

import com.cooba.dto.CustomerInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CustomerSearchResponse {
    private List<CustomerInfo> customerInfos;
}
