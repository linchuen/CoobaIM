package com.cooba.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "password")
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
