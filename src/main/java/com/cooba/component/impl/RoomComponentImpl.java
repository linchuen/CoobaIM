package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.component.RoomComponent;
import com.cooba.service.MessageService;
import com.cooba.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ObjectLayer
@RequiredArgsConstructor
public class RoomComponentImpl implements RoomComponent {
    private final RoomService roomService;
    private final MessageService messageService;

    @Override
    public void build() {
        roomService.build();
    }

    @Override
    public void destroy() {
        roomService.destroy();
    }

    @Override
    public void invite() {
        roomService.addUser();

        messageService.sendToRoom();
    }

    @Override
    public void evict() {
        roomService.deleteUser();

        messageService.sendToRoom();
    }
}
