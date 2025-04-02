package com.cooba.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlatformEnum {
    web(0),
    app(1),
    ios(2),
    android(3),
    ;

    @EnumValue
    private final int roomRoleId;
}
