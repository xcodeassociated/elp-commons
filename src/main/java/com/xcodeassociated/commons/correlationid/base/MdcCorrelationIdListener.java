package com.xcodeassociated.commons.correlationid.base;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class MdcCorrelationIdListener implements CorrelationIdListener {

    private static final String CORRELATION_ID_LOGBACK_PROPERTY_NAME = "correlation_id";

    @Override
    public void notify(String correlationId) {
        MDC.put(CORRELATION_ID_LOGBACK_PROPERTY_NAME, correlationId);
    }

    @Override
    public void clear() {
        MDC.remove(CORRELATION_ID_LOGBACK_PROPERTY_NAME);
    }

}
