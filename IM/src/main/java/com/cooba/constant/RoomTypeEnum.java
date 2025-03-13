package com.cooba.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomTypeEnum {
    PERSONAL(0),
    GROUP(1),
    ;

    @EnumValue
    private final int roomTypeId;
}
