package com.cooba.dto.response;

import com.cooba.dto.FriendApplyInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FriendSearchApplyResponse {
    private List<FriendApplyInfo> applicants;
}
