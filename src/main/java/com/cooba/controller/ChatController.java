package com.cooba.controller;

import com.cooba.component.ChatComponent;
import com.cooba.dto.request.ChatLoadRequest;
import com.cooba.dto.request.SpeakRequest;
import com.cooba.dto.response.ChatLoadResponse;
import com.cooba.dto.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@Tag(name = "/chat", description = "會話管理")
public class ChatController {
    private final ChatComponent chatComponent;

    @PostMapping("/user")
    @Operation(summary = "傳訊息至用戶")
    public ResultResponse<?> speakToUser(@RequestBody SpeakRequest request) {
        chatComponent.speakToUser(request);
        return ResultResponse.builder().data(true).build();
    }

    @PostMapping("/room")
    @Operation(summary = "傳訊息至聊天室")
    public ResultResponse<?> speakToRoom(@RequestBody SpeakRequest request) {
        chatComponent.speakToRoom(request);
        return ResultResponse.builder().data(true).build();
    }

    @PostMapping("/all")
    @Operation(summary = "傳訊息至所有人")
    public ResultResponse<?> speakToAll(@RequestBody SpeakRequest request) {
        chatComponent.speakToAll(request);
        return ResultResponse.builder().data(true).build();
    }


    @PostMapping("/load")
    @Operation(summary = "取得聊天室內容")
    public ResultResponse<?> load(@RequestBody ChatLoadRequest request) {
        ChatLoadResponse response = chatComponent.load(request);
        return ResultResponse.builder().data(response).build();
    }
}
