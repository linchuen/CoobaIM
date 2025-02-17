package com.cooba.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomTypeEnum {
    PERSONAL(1),
    GROUP(2),
    ;

    @EnumValue
    private final int roomTypeId;
}
