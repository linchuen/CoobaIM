package com.cooba.component;

import com.cooba.dto.request.RoomBuildRequest;
import com.cooba.dto.request.RoomRequest;
import com.cooba.dto.request.RoomSearchRequest;
import com.cooba.dto.request.RoomUserRequest;
import com.cooba.dto.response.BuildRoomResponse;
import com.cooba.dto.response.RoomDestroyResponse;
import com.cooba.dto.response.RoomMemberResponse;
import com.cooba.dto.response.RoomSearchResponse;

public interface RoomComponent {

    BuildRoomResponse build(RoomBuildRequest request);

    RoomDestroyResponse destroy(RoomRequest request);

    void invite(RoomUserRequest request);

    void evict(RoomUserRequest request);

    void transferPermission(RoomUserRequest request);

    RoomSearchResponse search(RoomSearchRequest request);

    RoomMemberResponse search(RoomRequest request);
}
