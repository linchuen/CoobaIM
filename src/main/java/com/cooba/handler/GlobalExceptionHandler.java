package com.cooba.handler;

import brave.Tracer;
import com.cooba.dto.response.ResultResponse;
import com.cooba.exception.BaseException;
import com.cooba.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    public static final ResultResponse<?> response401 = ResultResponse.builder()
            .code(401)
            .errorMessage("Invalid Authorization")
            .build();
    public static final String response401Json = JsonUtil.toJson(response401);
    private final Tracer tracer;

    // 處理通用異常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultResponse<?> handleGlobalException(Exception ex, WebRequest request) {
        return ResultResponse.builder()
                .traceId(tracer.currentSpan().context().traceIdString())
                .errorMessage(ex.getMessage())
                .build();
    }

    // 處理特定異常，例如自定義異常
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResultResponse<?> handleResourceNotFoundException(BaseException ex, WebRequest request) {
        return ResultResponse.builder()
                .traceId(tracer.currentSpan().context().traceIdString())
                .errorMessage(ex.getMessage())
                .build();
    }
}
