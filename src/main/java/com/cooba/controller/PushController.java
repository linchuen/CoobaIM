package com.cooba.controller;

import com.cooba.dto.request.MessageRequest;
import com.cooba.tio.TioWebSocketServerBootstrap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/msg")
@RequiredArgsConstructor
public class PushController {
    private final TioWebSocketServerBootstrap bootstrap;

    @PostMapping("/user")
    public void pushMessageUser(@RequestBody MessageRequest request) {
    }

    @PostMapping("/group")
    public void pushMessageGroup(@RequestBody MessageRequest request) {
    }

    @PostMapping("/all")
    public void pushMessageAll(@RequestBody MessageRequest request) {
    }
}
