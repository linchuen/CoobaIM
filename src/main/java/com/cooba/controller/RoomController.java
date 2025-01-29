package com.cooba.controller;

import com.cooba.component.RoomComponent;
import com.cooba.dto.request.RoomRequest;
import com.cooba.dto.request.RoomUserRequest;
import com.cooba.dto.response.BuildRoomResponse;
import com.cooba.dto.response.DestroyRoomResponse;
import com.cooba.dto.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomComponent roomComponent;

    @PostMapping("/build")
    public ResultResponse<?> buildRoom(@RequestBody RoomRequest request) {
        BuildRoomResponse response = roomComponent.build(request);
        return ResultResponse.builder().data(response).build();
    }

    @DeleteMapping("/destroy")
    public ResultResponse<?> destroyRoom(@RequestBody RoomRequest request) {
        DestroyRoomResponse response = roomComponent.destroy(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/invite")
    public ResultResponse<?> inviteUser(@RequestBody RoomUserRequest request) {
        roomComponent.invite(request);
        return ResultResponse.builder().build();
    }

    @DeleteMapping("/evict")
    public ResultResponse<?> evictUser(@RequestBody RoomUserRequest request) {
        roomComponent.evict(request);
        return ResultResponse.builder().build();
    }
}
