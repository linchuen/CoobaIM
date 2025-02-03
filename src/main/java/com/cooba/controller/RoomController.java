package com.cooba.controller;

import com.cooba.component.RoomComponent;
import com.cooba.dto.request.RoomRequest;
import com.cooba.dto.request.RoomUserRequest;
import com.cooba.dto.response.BuildRoomResponse;
import com.cooba.dto.response.DestroyRoomResponse;
import com.cooba.dto.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
@Tag(name = "/room", description = "聊天室管理")
public class RoomController {
    private final RoomComponent roomComponent;

    @PostMapping("/build")
    @Operation(summary = "建立聊天室")
    public ResultResponse<?> buildRoom(@RequestBody RoomRequest request) {
        BuildRoomResponse response = roomComponent.build(request);
        return ResultResponse.builder().data(response).build();
    }

    @DeleteMapping("/destroy")
    @Operation(summary = "刪除聊天室")
    public ResultResponse<?> destroyRoom(@RequestBody RoomRequest request) {
        DestroyRoomResponse response = roomComponent.destroy(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/invite")
    @Operation(summary = "用戶邀請", description = "需要管理員權限")
    public ResultResponse<?> inviteUser(@RequestBody RoomUserRequest request) {
        roomComponent.invite(request);
        return ResultResponse.builder().data(true).build();
    }

    @DeleteMapping("/evict")
    @Operation(summary = "用戶驅除", description = "需要管理員權限")
    public ResultResponse<?> evictUser(@RequestBody RoomUserRequest request) {
        roomComponent.evict(request);
        return ResultResponse.builder().data(true).build();
    }
}
