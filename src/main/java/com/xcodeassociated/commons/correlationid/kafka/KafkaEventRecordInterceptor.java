package com.xcodeassociated.commons.correlationid.kafka;

import com.xcodeassociated.commons.correlationid.base.ThreadCorrelationId;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventRecordInterceptor extends KafkaRecordInterceptor<String, Object> {

    public KafkaEventRecordInterceptor(ThreadCorrelationId threadCorrelationId) {
        super(threadCorrelationId);
    }

}
