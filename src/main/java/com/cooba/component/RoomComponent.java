package com.cooba.component;

import com.cooba.dto.request.RoomRequest;
import com.cooba.dto.request.RoomSearchRequest;
import com.cooba.dto.request.RoomUserRequest;
import com.cooba.dto.response.BuildRoomResponse;
import com.cooba.dto.response.RoomDestroyResponse;
import com.cooba.dto.response.RoomSearchResponse;

public interface RoomComponent {

    BuildRoomResponse build(RoomRequest request);

    RoomDestroyResponse destroy(RoomRequest request);

    void invite(RoomUserRequest request);

    void evict(RoomUserRequest request);

    RoomSearchResponse search(RoomSearchRequest request);
}
