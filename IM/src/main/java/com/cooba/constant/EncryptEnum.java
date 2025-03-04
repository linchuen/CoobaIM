package com.cooba.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EncryptEnum {
    AES("AES", 16);

    private final String instance;

    private final int keySize;
}
