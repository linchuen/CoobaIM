package com.cooba.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultResponse<T> {
    private String traceId;
    private int code;
    private T data;
    private String errorMessage;
}
