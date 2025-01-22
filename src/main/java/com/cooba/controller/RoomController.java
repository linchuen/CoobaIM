package com.cooba.controller;

import com.cooba.dto.request.RoomRequest;
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
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final TioWebSocketServerBootstrap bootstrap;

    @PostMapping("/join")
    public void joinRoom(@RequestBody RoomRequest request) {
        Tio.bindGroup(bootstrap.getServerTioConfig(), request.getUid(), request.getGroupId());

        WsResponse text = WsResponse.fromText(request.getUid() + " 加入房間", StandardCharsets.UTF_8.name());
        Tio.sendToGroup(bootstrap.getServerTioConfig(), request.getGroupId(), text);
    }

    @PostMapping("/leave")
    public void leaveRoom(@RequestBody RoomRequest request) {
        Tio.unbindGroup(bootstrap.getServerTioConfig(), request.getUid(), request.getGroupId());

        WsResponse text = WsResponse.fromText(request.getUid() + " 離開房間", StandardCharsets.UTF_8.name());
        Tio.sendToGroup(bootstrap.getServerTioConfig(), request.getGroupId(), text);
    }
}
