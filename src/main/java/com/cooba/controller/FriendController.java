package com.cooba.controller;

import com.cooba.component.UserComponent;
import com.cooba.dto.request.FriendRemoveRequest;
import com.cooba.dto.request.FriendRequest;
import com.cooba.dto.response.ApplyFriendResponse;
import com.cooba.dto.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
@Tag(name = "/friend", description = "好友管理")
public class FriendController {
    private final UserComponent userComponent;

    @PostMapping("/apply")
    @Operation(summary = "好友申請")
    public ResultResponse<?> apply(@RequestBody FriendRequest request) {
        ApplyFriendResponse response = userComponent.applyFriend(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/permit")
    @Operation(summary = "好友審核")
    public ResultResponse<?> permit(@RequestBody FriendRequest request) {
        userComponent.permitFriendApply(request);
        return ResultResponse.builder().data(true).build();
    }

    @DeleteMapping("/remove")
    @Operation(summary = "好友刪除")
    public ResultResponse<?> remove(@RequestBody FriendRemoveRequest request) {
        userComponent.removeFriend(request);
        return ResultResponse.builder().data(true).build();
    }
}
