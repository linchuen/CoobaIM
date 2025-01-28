package com.cooba.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomRoleEnum {
    MASTER(1),
    MANAGER(2),
    MEMBER(3),
    ;

    @EnumValue
    private final int roomRoleId;
}
