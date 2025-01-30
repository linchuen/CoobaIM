package com.cooba.controller;

import com.cooba.component.UserComponent;
import com.cooba.dto.request.SpeakRequest;
import com.cooba.dto.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/speak")
@RequiredArgsConstructor
public class SpeakController {
    private final UserComponent userComponent;

    @PostMapping("/user")
    public ResultResponse<?> speakToUser(@RequestBody SpeakRequest request) {
        userComponent.speakToUser(request);
        return ResultResponse.builder().data(true).build();
    }

    @PostMapping("/room")
    public ResultResponse<?> speakToRoom(@RequestBody SpeakRequest request) {
        userComponent.speakToRoom(request);
        return ResultResponse.builder().data(true).build();
    }

    @PostMapping("/all")
    public ResultResponse<?> speakToAll(@RequestBody SpeakRequest request) {
        userComponent.speakToAll(request);
        return ResultResponse.builder().data(true).build();
    }
}
