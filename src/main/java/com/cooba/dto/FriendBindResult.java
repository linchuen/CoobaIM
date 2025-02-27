package com.cooba.dto;

import com.cooba.entity.Friend;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FriendBindResult {
    private Friend applyUser;
    private Friend permitUser;
}
