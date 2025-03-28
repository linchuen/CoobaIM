package com.cooba.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "minio")
public class Minio {
    public static final String BUCKET_PREFIX = "bucket-";
    private String endpoint;
    private String accessKey;
    private String secretKey;
}
