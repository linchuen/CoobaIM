package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.annotation.WebhookTrigger;
import com.cooba.aop.UserThreadLocal;
import com.cooba.component.UserComponent;
import com.cooba.constant.ErrorEnum;
import com.cooba.constant.EventEnum;
import com.cooba.constant.RoomTypeEnum;
import com.cooba.constant.WebhookEnum;
import com.cooba.core.SocketConnection;
import com.cooba.dto.FriendApplyInfo;
import com.cooba.dto.FriendBindResult;
import com.cooba.dto.UserInfo;
import com.cooba.dto.request.*;
import com.cooba.dto.response.*;
import com.cooba.entity.*;
import com.cooba.exception.BaseException;
import com.cooba.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@ObjectLayer
@RequiredArgsConstructor
public class UserComponentImpl implements UserComponent {
    private final UserService userService;
    private final RoomService roomService;
    private final MessageService messageService;
    private final FriendService friendService;
    private final SessionService sessionService;
    private final UserThreadLocal userThreadLocal;
    private final SocketConnection socketConnection;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        long userId = userService.register(user);
        return RegisterResponse.builder()
                .userId(userId)
                .build();
    }

    @Override
    public User getInfo(long userId) {
        return userService.getInfo(userId);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userService.getInfo(request.getEmail());

        userService.verifyPassword(user, request.getPassword());

        Session session = sessionService.add(user);

        userService.getAllRooms(user.getId()).forEach(userService::connectRoom);

        return LoginResponse.builder()
                .name(user.getName())
                .userId(session.getUserId())
                .platform(session.getPlatform())
                .token(session.getToken())
                .loginTime(session.getLoginTime())
                .expireTime(session.getExpireTime())
                .build();
    }

    @Override
    public LogoutResponse logout(LogoutRequest request) {
        User user = userService.getInfo(request.getUserId());

        LocalDateTime logoutTime = sessionService.remove(user);

        userService.getAllRooms(user.getId()).forEach(userService::disconnectRoom);

        return LogoutResponse.builder()
                .logoutTime(logoutTime)
                .build();
    }

    @Override
    public boolean isOnline(long userId) {
        return sessionService.getInfo(userId).getEnable();
    }

    @Override
    public RoomResponse enterRoom(RoomUserRequest request) {
        RoomUser roomUser = new RoomUser();
        roomUser.setRoomId(request.getRoomId());
        roomUser.setUserId(request.getUserId());

        roomService.addUser(roomUser);

        userService.connectRoom(roomUser);

        return RoomResponse.builder()
                .roomId(request.getRoomId())
                .userId(request.getUserId())
                .build();
    }

    @Override
    public RoomResponse leaveRoom(RoomUserRequest request) {
        RoomUser roomUser = new RoomUser();
        roomUser.setRoomId(request.getRoomId());
        roomUser.setUserId(request.getUserId());

        roomService.deleteUser(roomUser);

        userService.disconnectRoom(roomUser);

        return RoomResponse.builder()
                .roomId(request.getRoomId())
                .userId(request.getUserId())
                .build();
    }

    @Override
    @WebhookTrigger(WebhookEnum.APPLY_FRIEND)
    public FriendApplyResponse applyFriend(FriendRequest request) {
        if ((request.getApplyUserId().equals(request.getPermitUserId()))) {
            throw new BaseException(ErrorEnum.BUSINESS_ERROR);
        }

        FriendApply friendApply = new FriendApply();
        friendApply.setApplyUserId(request.getApplyUserId());
        friendApply.setPermitUserId(request.getPermitUserId());

        long applyId = friendService.apply(friendApply);

        UserInfo userInfo = userThreadLocal.get();
        FriendApplyInfo applyInfo = new FriendApplyInfo();
        applyInfo.setId(applyId);
        applyInfo.setApplyId(userInfo.getId());
        applyInfo.setName(userInfo.getName());
        socketConnection.sendUserEvent(String.valueOf(request.getPermitUserId()), EventEnum.FRIEND_APPLY, applyInfo);
        return FriendApplyResponse.builder()
                .applyId(applyId)
                .build();
    }

    @Override
    public FriendPermitResponse permitFriendApply(FriendRequest request) {
        Long permitUserId = request.getPermitUserId();
        Long applyUserId = request.getApplyUserId();

        long userId = userThreadLocal.getCurrentUserId();
        if (!Objects.equals(userId, permitUserId)) throw new BaseException(ErrorEnum.FORBIDDEN);

        FriendApply friendApply = new FriendApply();
        friendApply.setApplyUserId(applyUserId);
        friendApply.setPermitUserId(permitUserId);
        friendApply.setPermit(request.getIsPermit());

        if (!request.getIsPermit()) {
            friendService.bind(friendApply, () -> null);
            return FriendPermitResponse.builder().build();
        }

        Room room = new Room();
        room.setName(UUID.randomUUID().toString());
        room.setRoomTypeEnum(RoomTypeEnum.PERSONAL);
        long roomId = roomService.build(room, List.of(applyUserId));
        FriendBindResult friendBindResult = friendService.bind(friendApply, () -> room);

        socketConnection.sendUserEvent(String.valueOf(applyUserId), EventEnum.FRIEND_ADD, friendBindResult.getApplyUser());
        return FriendPermitResponse.builder()
                .roomId(roomId)
                .build();
    }

    @Override
    public void removeFriend(FriendRemoveRequest request) {
        Long userId = userThreadLocal.getCurrentUserId();

        FriendApply friendApply = new FriendApply();
        friendApply.setApplyUserId(userId);
        friendApply.setPermitUserId(request.getFriendUserId());

        friendService.unbind(friendApply);
    }

    @Override
    public FriendSearchResponse searchFriend(FriendSearchRequest request) {
        long userId = userThreadLocal.getCurrentUserId();

        List<Friend> friends = friendService.search(userId, request.getFriendUserIds());
        return FriendSearchResponse.builder()
                .friends(friends)
                .build();
    }

    @Override
    public FriendSearchApplyResponse searchFriendApply() {
        long userId = userThreadLocal.getCurrentUserId();

        List<FriendApplyInfo> friendApplyInfoList = friendService.searchApply(userId);
        return FriendSearchApplyResponse.builder()
                .applicants(friendApplyInfoList)
                .build();
    }
}
