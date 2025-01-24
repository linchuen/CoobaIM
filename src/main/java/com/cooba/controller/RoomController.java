package com.cooba.controller;

import com.cooba.component.RoomComponent;
import com.cooba.dto.request.RoomRequest;
import com.cooba.dto.request.RoomUserRequest;
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
        roomComponent.build(request);
        return ResultResponse.builder().build();
    }

    @DeleteMapping("/destroy")
    public ResultResponse<?> destroyRoom(@RequestBody RoomRequest request) {
        roomComponent.destroy(request);
        return ResultResponse.builder().build();
    }

    @PostMapping("/invite")
    public ResultResponse<?> inviteUser(@RequestBody RoomUserRequest request) {
        roomComponent.invite(request);
        return ResultResponse.builder().build();
    }

    @PostMapping("/evict")
    public ResultResponse<?> evictUser(@RequestBody RoomUserRequest request) {
        roomComponent.evict(request);
        return ResultResponse.builder().build();
    }
}
