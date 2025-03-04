package com.cooba.aop;

import com.cooba.annotation.WebhookTrigger;
import com.cooba.constant.Webhook;
import com.cooba.constant.WebhookEnum;
import com.cooba.dto.WebhookPayload;
import com.cooba.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class WebhookAspect {
    private final Webhook webhookConfig;
    private final WebClient webClient = WebClient.create();

    @Before("@annotation(webhookTrigger)")
    public void sendBeforeWebhook(JoinPoint joinPoint, WebhookTrigger webhookTrigger) {
        if (!webhookConfig.isEnabled()) return;

        Map<WebhookEnum, String> eventMap = webhookConfig.getEvent();
        WebhookEnum webhookEnum = webhookTrigger.value();
        String url = eventMap.get(webhookEnum);
        String domain = webhookConfig.getDomain();
        if (domain.isEmpty() || url == null) {
            return;
        }

        WebhookPayload payload = new WebhookPayload();
        payload.setMethod(webhookEnum.name());
        payload.setRequest(joinPoint.getArgs()[0]);
        payload.setType("before");

        sendWebhookWithRetry(domain + url, payload);
    }

    @AfterReturning(value = "@annotation(webhookTrigger)", returning = "result")
    public void sendAfterWebhook(JoinPoint joinPoint, Object result, WebhookTrigger webhookTrigger) {
        if (!webhookConfig.isEnabled()) return;

        Map<WebhookEnum, String> eventMap = webhookConfig.getEvent();
        WebhookEnum webhookEnum = webhookTrigger.value();
        String url = eventMap.get(webhookEnum);
        String domain = webhookConfig.getDomain();
        if (domain.isEmpty() || url == null) {
            return;
        }

        WebhookPayload payload = new WebhookPayload();
        payload.setMethod(webhookEnum.name());
        payload.setRequest(joinPoint.getArgs()[0]);
        payload.setResult(result);
        payload.setType("after");

        sendWebhookWithRetry(domain + url, payload);
    }

    private void sendWebhookWithRetry(String url, WebhookPayload payload) {
        webClient.post()
                .uri(url)
                .body(Mono.just(payload), WebhookPayload.class)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))) // 重試 3 次，每次間隔 2 秒
                .doOnError(error -> System.err.println("Webhook failed after retries: " + error.getMessage()))
                .subscribe(response -> System.out.println("Webhook success: " + response));
    }
}
