package com.cooba.controller;

import com.cooba.component.UserComponent;
import com.cooba.dto.request.FriendRequest;
import com.cooba.dto.response.ApplyFriendResponse;
import com.cooba.dto.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {
    private final UserComponent userComponent;

    @PostMapping("/add")
    public ResultResponse<?> apply(@RequestBody FriendRequest request) {
        ApplyFriendResponse response = userComponent.applyFriend(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/permit")
    public ResultResponse<?> permit(@RequestBody FriendRequest request) {
        userComponent.permitFriendApply(request);
        return ResultResponse.builder().data(true).build();
    }

    @PostMapping("/remove")
    public ResultResponse<?> remove(@RequestBody FriendRequest request) {
        userComponent.removeFriend(request);
        return ResultResponse.builder().data(true).build();
    }
}
