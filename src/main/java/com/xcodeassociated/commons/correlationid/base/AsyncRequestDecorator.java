package com.xcodeassociated.commons.correlationid.base;

import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class AsyncRequestDecorator implements TaskDecorator {

    @Nonnull
    @Override
    public Runnable decorate(@Nonnull Runnable runnable) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return () -> {
            try {
                RequestContextHolder.setRequestAttributes(requestAttributes);
                MDC.setContextMap(contextMap);
                runnable.run();
            } finally {
                MDC.clear();
                RequestContextHolder.resetRequestAttributes();
            }
        };
    }
}
