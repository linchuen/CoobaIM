package com.cooba.component;

import com.cooba.dto.request.RoomRequest;
import com.cooba.dto.request.RoomUserRequest;
import com.cooba.dto.response.BuildRoomResponse;
import com.cooba.dto.response.DestroyRoomResponse;

public interface RoomComponent {

    BuildRoomResponse build(RoomRequest request);

    DestroyRoomResponse destroy(RoomRequest request);

    void invite(RoomUserRequest request);

    void evict(RoomUserRequest request);
}
