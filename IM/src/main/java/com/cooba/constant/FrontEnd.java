package com.cooba.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "front-end")
public class FrontEnd {
    private String url;
    private String[] allPermitPaths;
}
