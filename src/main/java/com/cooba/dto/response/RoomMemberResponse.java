package com.cooba.dto.response;

import com.cooba.entity.RoomUser;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RoomMemberResponse {
    private List<RoomUser> roomUsers;
}
