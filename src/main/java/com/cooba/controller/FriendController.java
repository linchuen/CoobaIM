package com.cooba.controller;

import com.cooba.component.UserComponent;
import com.cooba.dto.request.FriendRemoveRequest;
import com.cooba.dto.request.FriendRequest;
import com.cooba.dto.response.ApplyFriendResponse;
import com.cooba.dto.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {
    private final UserComponent userComponent;

    @PostMapping("/apply")
    public ResultResponse<?> apply(@RequestBody FriendRequest request) {
        ApplyFriendResponse response = userComponent.applyFriend(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/permit")
    public ResultResponse<?> permit(@RequestBody FriendRequest request) {
        userComponent.permitFriendApply(request);
        return ResultResponse.builder().data(true).build();
    }

    @DeleteMapping("/remove")
    public ResultResponse<?> remove(@RequestBody FriendRemoveRequest request) {
        userComponent.removeFriend(request);
        return ResultResponse.builder().data(true).build();
    }
}
