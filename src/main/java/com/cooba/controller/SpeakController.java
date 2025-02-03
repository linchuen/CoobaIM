package com.cooba.controller;

import com.cooba.component.UserComponent;
import com.cooba.dto.request.SpeakRequest;
import com.cooba.dto.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/speak")
@RequiredArgsConstructor
@Tag(name = "/speak", description = "會話管理")
public class SpeakController {
    private final UserComponent userComponent;

    @PostMapping("/user")
    @Operation(summary = "傳訊息至用戶")
    public ResultResponse<?> speakToUser(@RequestBody SpeakRequest request) {
        userComponent.speakToUser(request);
        return ResultResponse.builder().data(true).build();
    }

    @PostMapping("/room")
    @Operation(summary = "傳訊息至聊天室")
    public ResultResponse<?> speakToRoom(@RequestBody SpeakRequest request) {
        userComponent.speakToRoom(request);
        return ResultResponse.builder().data(true).build();
    }

    @PostMapping("/all")
    @Operation(summary = "傳訊息至所有人")
    public ResultResponse<?> speakToAll(@RequestBody SpeakRequest request) {
        userComponent.speakToAll(request);
        return ResultResponse.builder().data(true).build();
    }
}
