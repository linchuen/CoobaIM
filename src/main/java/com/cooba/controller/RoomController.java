package com.cooba.controller;

import com.cooba.component.RoomComponent;
import com.cooba.dto.request.RoomBuildRequest;
import com.cooba.dto.request.RoomRequest;
import com.cooba.dto.request.RoomSearchRequest;
import com.cooba.dto.request.RoomUserRequest;
import com.cooba.dto.response.*;
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
    public ResultResponse<?> buildRoom(@RequestBody RoomBuildRequest request) {
        BuildRoomResponse response = roomComponent.build(request);
        return ResultResponse.builder().data(response).build();
    }

    @DeleteMapping("/destroy")
    @Operation(summary = "刪除聊天室")
    public ResultResponse<?> destroyRoom(@RequestBody RoomRequest request) {
        RoomDestroyResponse response = roomComponent.destroy(request);
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

    @PostMapping("/search")
    @Operation(summary = "聊天室列表")
    public ResultResponse<?> searchRoom(@RequestBody RoomSearchRequest request) {
        RoomSearchResponse response = roomComponent.search(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/search/users")
    @Operation(summary = "聊天室成員")
    public ResultResponse<?> searchUser(@RequestBody RoomRequest request) {
        RoomMemberResponse response = roomComponent.search(request);
        return ResultResponse.builder().data(response).build();
    }
}
