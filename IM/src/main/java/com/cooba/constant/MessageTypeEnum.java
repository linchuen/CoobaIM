package com.cooba.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageTypeEnum {
    TEXT(0),
    IMAGE(1),
    VIDEO(2),
    FILE(3),
    ;

    @EnumValue
    private final int messageType;
}
