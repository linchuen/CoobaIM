package com.cooba.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultResponse<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String traceId;
    private int code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private String errorMessage;
}
