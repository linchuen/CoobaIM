package com.cooba.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/hello") // 前端發送請求時 "/app/hello"
    @SendTo("/topic/greetings") // 廣播到 "/topic/greetings"
    public String sendMessage(String message) {
        return "Hello, " + message;
    }
}