package com.cooba.constant;

import lombok.Data;

@Data
public class Minio {
    public static final String BUCKET_PREFIX = "bucket-";
    private String endpoint;
    private String accessKey;
    private String secretKey;
}
