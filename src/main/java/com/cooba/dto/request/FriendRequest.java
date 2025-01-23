package com.cooba.dto.request;

import lombok.Data;

@Data
public class FriendRequest {
    private Long applyUserId;
    private Long permitUserId;
    private boolean isPermit;
}
