package com.cooba.controller;

import com.cooba.component.RoomComponent;
import com.cooba.dto.request.RoomRequest;
import com.cooba.dto.request.RoomUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomComponent roomComponent;

    @PostMapping("/build")
    public void buildRoom(@RequestBody RoomRequest request) {
        roomComponent.build(request);
    }

    @DeleteMapping("/destroy")
    public void destroyRoom(@RequestBody RoomRequest request) {
        roomComponent.destroy(request);
    }

    @PostMapping("/build")
    public void inviteUser(@RequestBody RoomUserRequest request) {
        roomComponent.invite(request);
    }

    @PostMapping("/build")
    public void evictUser(@RequestBody RoomUserRequest request) {
        roomComponent.evict(request);
    }
}
