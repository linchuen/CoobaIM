package com.cooba.component;

import com.cooba.dto.request.RoomRequest;
import com.cooba.dto.request.RoomUserRequest;

public interface RoomComponent {

    void build(RoomRequest request);

    void destroy(RoomRequest request);

    void invite(RoomUserRequest request);

    void evict(RoomUserRequest request);
}
