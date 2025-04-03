package com.cooba.dto.response;

import com.cooba.entity.UserDetail;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDetailResponse {
    private UserDetail userDetail;
}
