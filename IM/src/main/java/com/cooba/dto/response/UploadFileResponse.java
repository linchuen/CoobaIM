package com.cooba.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UploadFileResponse {
    private String fileName;
    private String url;
}
