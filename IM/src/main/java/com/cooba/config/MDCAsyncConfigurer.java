package com.cooba.config;

import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.Executor;

@Configuration
public class MDCAsyncConfigurer implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setTaskDecorator((runnable) -> {
            try {
                // Right now: Web thread context !
                // Grab the current thread MDC data
                Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();

                return () -> {
                    // Right now: @Async thread context !
                    // Restore the Web thread context's MDC data
                    MDC.setContextMap(copyOfContextMap);
                    runnable.run();
                };
            } finally {
                MDC.clear();
            }
        });
        executor.initialize();
        return executor;
    }

}
