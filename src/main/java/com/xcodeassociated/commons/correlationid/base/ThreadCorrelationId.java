package com.xcodeassociated.commons.correlationid.base;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class ThreadCorrelationId {

    public static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    private final ThreadLocal<String> id = new ThreadLocal<>();
    private final CorrelationIdListener listener;

    public ThreadCorrelationId(CorrelationIdListener listener) {
        this.listener = listener;
    }

    public Optional<String> getCorrelationId() {
        return Optional.ofNullable(id.get());
    }

    public String getOrCreateCorrelationId() {
        return getCorrelationId().orElseGet(this::createNewCorrelationId);
    }

    public void setCorrelationId(String correlationId) {
        id.set(correlationId);
        listener.notify(correlationId);
    }

    public String createNewCorrelationId() {
        String correlationId = UUID.randomUUID().toString();
        setCorrelationId(correlationId);
        return correlationId;
    }

    public void clearCorrelationId() {
        id.set(null);
        clearContext();
    }

    public void clearContext() {
        listener.clear();
    }

}
