package com.xcodeassociated.commons.correlationid.aspect;

import static com.xcodeassociated.commons.correlationid.base.CorrelationIdConstants.CORRELATION_ID_KEY;

import java.util.Map;

import com.xcodeassociated.commons.correlationid.base.ThreadCorrelationId;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CorrelationIdAsyncAspect {

    private final ThreadCorrelationId threadCorrelationId;

    @Before("@annotation(org.springframework.scheduling.annotation.Async)")
    public void beforeAsyncAnnotation() {
        Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        threadCorrelationId.setCorrelationId(copyOfContextMap.get(CORRELATION_ID_KEY));
    }

    @After("@annotation(org.springframework.scheduling.annotation.Async)")
    public void afterAsyncAnnotation() {
        threadCorrelationId.clearCorrelationId();
    }

}
