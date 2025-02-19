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

    @PostMapping("/load")
    @Operation(summary = "取得聊天室內容")
    public ResultResponse<?> load(@RequestBody ChatLoadRequest request) {
        ChatLoadResponse response = chatComponent.load(request);
        return ResultResponse.builder().data(response).build();
    }
}
