package com.cooba.controller;

import com.cooba.component.UserComponent;
import com.cooba.dto.request.FriendRemoveRequest;
import com.cooba.dto.request.FriendRequest;
import com.cooba.dto.request.FriendSearchRequest;
import com.cooba.dto.response.FriendApplyResponse;
import com.cooba.dto.response.FriendPermitResponse;
import com.cooba.dto.response.FriendSearchResponse;
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
        FriendApplyResponse response = userComponent.applyFriend(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/permit")
    @Operation(summary = "好友審核")
    public ResultResponse<?> permit(@RequestBody FriendRequest request) {
        FriendPermitResponse response = userComponent.permitFriendApply(request);
        return ResultResponse.builder().data(response).build();
    }

    @DeleteMapping("/remove")
    @Operation(summary = "好友刪除")
    public ResultResponse<?> remove(@RequestBody FriendRemoveRequest request) {
        userComponent.removeFriend(request);
        return ResultResponse.builder().data(true).build();
    }

    @PostMapping("/search")
    @Operation(summary = "好友搜尋")
    public ResultResponse<?> search(@RequestBody FriendSearchRequest request) {
        FriendSearchResponse response = userComponent.searchFriend(request);
        return ResultResponse.builder().data(response).build();
    }
}
