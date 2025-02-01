package com.cooba.aop;

import brave.Tracer;
import brave.propagation.TraceContext;
import com.cooba.dto.response.ResultResponse;

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
    private final UserThreadLocal userThreadLocal;

    @Around("execution(public * com.cooba.controller.**.*(..))")
    public Object addTraceId(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        if (result instanceof ResultResponse) {
            TraceContext context = tracer.currentSpan().context();

            String traceId = context.traceIdString();
            ((ResultResponse<?>) result).setTraceId(traceId);
        }
        userThreadLocal.remove();
        return result;
    }

}
