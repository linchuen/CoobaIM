package com.cooba.aop;

import brave.Tracer;
import com.cooba.constant.ErrorEnum;
import com.cooba.dto.response.ResultResponse;
import com.cooba.exception.BaseException;
import com.cooba.util.JsonUtil;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;



import static com.cooba.constant.ErrorEnum.INVALID_AUTHORIZATION;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    public static final ResultResponse<?> response401 = ResultResponse.builder()
            .code(INVALID_AUTHORIZATION.getCode())
            .errorMessage(INVALID_AUTHORIZATION.getMessage())
            .build();
    public static final String response401Json = JsonUtil.toJson(response401);
    private final Tracer tracer;

    // 處理通用異常
    @ExceptionHandler({ValidationException.class, MethodArgumentNotValidException.class})
    @ResponseBody
    public ResultResponse<?> handleValidationException(Exception ex, WebRequest request) {
        return ResultResponse.builder()
                .code(ErrorEnum.INVALID_REQUEST.getCode())
                .traceId(tracer.currentSpan().context().traceIdString())
                .errorMessage(ex.getMessage())
                .logMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResultResponse<?> handleBaseException(BaseException ex, WebRequest request) {
        ErrorEnum errorEnum = ex.getErrorEnum() == null ? ErrorEnum.BUSINESS_ERROR : ex.getErrorEnum();
        return ResultResponse.builder()
                .traceId(tracer.currentSpan().context().traceIdString())
                .errorMessage(errorEnum.getMessage())
                .code(errorEnum.getCode())
                .logMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultResponse<?> handleGlobalException(Exception ex, WebRequest request) {
        return ResultResponse.builder()
                .code(ErrorEnum.BUSINESS_ERROR.getCode())
                .traceId(tracer.currentSpan().context().traceIdString())
                .errorMessage(ex.getMessage())
                .logMessage(ex.getMessage())
                .build();
    }
}
