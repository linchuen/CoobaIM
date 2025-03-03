package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.aop.UserThreadLocal;
import com.cooba.component.LiveComponent;
import com.cooba.constant.ErrorEnum;
import com.cooba.constant.EventEnum;
import com.cooba.core.SocketConnection;
import com.cooba.dto.request.LiveBuildRequest;
import com.cooba.dto.request.LiveCloseRequest;
import com.cooba.dto.request.ParticipantTokenRequest;
import com.cooba.dto.response.LiveCall;
import com.cooba.entity.RoomUser;
import com.cooba.exception.BaseException;
import com.cooba.service.LiveKitService;
import com.cooba.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
@ObjectLayer
@RequiredArgsConstructor
public class LiveComponentImpl implements LiveComponent {
    private final LiveKitService liveKitService;
    private final RoomService roomService;
    private final UserThreadLocal userThreadLocal;
    private final SocketConnection socketConnection;

    @Override
    public LiveCall createRoom(LiveBuildRequest request) {
        long roomId = request.getRoomId();
        String passcode = request.getPasscode();
        String type = request.getType();
        long userId = userThreadLocal.getCurrentUserId();
        List<RoomUser> roomUsers = roomService.getRoomUsers(roomId);
        if (roomUsers.stream().noneMatch(roomUser -> roomUser.getUserId() == userId)) {
            throw new BaseException(ErrorEnum.ROOM_USER_NOT_EXIST);
        }

        int total = roomUsers.size();
        try {
            String roomName = liveKitService.createRoom(total + 2);
            LiveCall masterCall = null;
            for (RoomUser user : roomUsers) {
                Long roomUserId = user.getUserId();
                String accessToken = liveKitService.createAccessToken(
                        user.getShowName(),
                        String.valueOf(roomUserId),
                        roomName,
                        passcode);

                if (userId == roomUserId) {
                    masterCall = new LiveCall(roomId, roomName, passcode, accessToken, type);
                } else {
                    LiveCall liveCall = new LiveCall(roomId, roomName, passcode, accessToken, type);
                    socketConnection.sendUserEvent(String.valueOf(roomUserId), EventEnum.LIVE_CALL, liveCall);
                }
            }
            return masterCall;
        } catch (IOException exception) {
            throw new BaseException(ErrorEnum.NETWORK_ERROR);
        }
    }

    @Override
    public void deleteRoom(LiveCloseRequest request) {
        long roomId = request.getRoomId();
        long userId = userThreadLocal.getCurrentUserId();
        List<RoomUser> roomUsers = roomService.getRoomUsers(roomId);
        if (roomUsers.stream().noneMatch(roomUser -> roomUser.getUserId() == userId)) {
            throw new BaseException(ErrorEnum.ROOM_USER_NOT_EXIST);
        }
        int total = roomUsers.size();
        try {
            if (total == 2) {
                liveKitService.deleteRoom(request.getRoomName());
            }
        } catch (IOException e) {
            throw new BaseException(ErrorEnum.NETWORK_ERROR);
        }
    }

    @Override
    public String createAccessToken(ParticipantTokenRequest request) {
        return liveKitService.createAccessToken(
                request.getName(),
                request.getIdentity(),
                request.getRoomName(),
                request.getPasscode());
    }
}
