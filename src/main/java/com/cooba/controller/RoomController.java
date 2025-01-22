package com.cooba.controller;

import com.cooba.component.RoomComponent;
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
    private final RoomComponent roomComponent;

    @PostMapping("/build")
    public void buildRoom(@RequestBody RoomRequest request) {
        roomComponent.build();
    }

    @PostMapping("/leave")
    public void leaveRoom(@RequestBody RoomRequest request) {

    }
}
