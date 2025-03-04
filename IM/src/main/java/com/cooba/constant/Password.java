package com.cooba.constant;

import lombok.Data;

import java.util.Map;

@Data
public class Password {
    private Map<String, PasswordType> manager;

    @Data
    public static class PasswordType {
        private String type;
        private String secret;

        public EncryptEnum getType(){
            return EncryptEnum.valueOf(this.type);
        }
    }
}
