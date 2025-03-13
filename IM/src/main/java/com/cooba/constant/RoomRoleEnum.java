package com.cooba.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomRoleEnum {
    MASTER(0),
    MANAGER(1),
    MEMBER(2),
    ;

    @EnumValue
    private final int roomRoleId;
}
