package com.cooba.aop;

import com.cooba.dto.response.ResultResponse;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class TraceAspect {
    private final Tracer tracer;

    @Around("execution(public * com.cooba.controller.**.*(..))")
    public Object addTraceId(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        if (result instanceof ResultResponse) {
            TraceContext context = tracer.currentTraceContext().context();

            if (context == null) {
                return result;
            }

            String traceId = context.traceId();
            ((ResultResponse<?>) result).setTraceId(traceId);
        }
        return result;
    }

}
