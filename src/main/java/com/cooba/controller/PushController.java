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
        Tio.sendToUser(bootstrap.getServerTioConfig(), request.getUid(), WsResponse.fromText(request.getMsg(), StandardCharsets.UTF_8.name()));
    }

    @PostMapping("/group")
    public void pushMessageGroup(@RequestBody MessageRequest request) {
        Tio.sendToGroup(bootstrap.getServerTioConfig(), request.getGroupId(), WsResponse.fromText(request.getMsg(), StandardCharsets.UTF_8.name()));
    }

    @PostMapping("/all")
    public void pushMessageAll(@RequestBody MessageRequest request) {
        Tio.sendToAll(bootstrap.getServerTioConfig(), WsResponse.fromText(request.getMsg(), StandardCharsets.UTF_8.name()));
    }
}
