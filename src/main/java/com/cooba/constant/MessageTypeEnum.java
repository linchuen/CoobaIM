package com.cooba.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageTypeEnum {
    TEXT(1),
    IMAGE(2),
    VIDEO(3),
    FILE(4),
    ;

    @EnumValue
    private final int messageType;
}
