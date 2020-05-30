package com.xcodeassociated.commons.correlationid.aspect;

import com.xcodeassociated.commons.correlationid.base.ThreadCorrelationId;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CorrelationIdScheduledAspect {

    private final ThreadCorrelationId threadCorrelationId;

    @Before("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void beforeScheduledAnnotation() {
        threadCorrelationId.getOrCreateCorrelationId();
    }

    @After("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void afterScheduledAnnotation() {
        threadCorrelationId.clearCorrelationId();
    }

}
