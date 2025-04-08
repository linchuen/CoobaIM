package com.cooba.aop;

import brave.Tracer;
import com.cooba.constant.ErrorEnum;
import com.cooba.dto.response.ResultResponse;
import com.cooba.exception.BaseException;
import com.cooba.exception.IMError;
import com.cooba.util.JsonUtil;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import static com.cooba.constant.ErrorEnum.INVALID_AUTHORIZATION;

@Slf4j
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
        log.error("ValidationException", ex);
        return ResultResponse.builder()
                .code(ErrorEnum.INVALID_REQUEST.getCode())
                .traceId(tracer.currentSpan().context().traceIdString())
                .errorMessage(ErrorEnum.INVALID_REQUEST.getMessage())
                .logMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResultResponse<?> handleBaseException(BaseException ex, WebRequest request) {
        IMError imError = ex.getError() == null ? ErrorEnum.BUSINESS_ERROR : ex.getError();
        log.error("BaseException", ex);
        return ResultResponse.builder()
                .traceId(tracer.currentSpan().context().traceIdString())
                .errorMessage(imError.getMessage())
                .code(imError.getCode())
                .logMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultResponse<?> handleGlobalException(Exception ex, WebRequest request) {
        log.error("GlobalException", ex);
        return ResultResponse.builder()
                .code(ErrorEnum.UNKNOWN_ERROR.getCode())
                .traceId(tracer.currentSpan().context().traceIdString())
                .errorMessage(ErrorEnum.UNKNOWN_ERROR.getMessage())
                .logMessage(ex.getMessage())
                .build();
    }
}
