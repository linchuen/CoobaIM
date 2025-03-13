package com.cooba.dto.response;

import com.cooba.entity.UserDetail;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CustomerDetailResponse {
    private List<UserDetail> userDetails;
}
