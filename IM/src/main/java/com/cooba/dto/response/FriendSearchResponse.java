package com.cooba.dto.response;


import com.cooba.entity.Friend;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FriendSearchResponse {
    private List<Friend> friends;
}
