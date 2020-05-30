package com.xcodeassociated.commons.correlationid.kafka;

import com.xcodeassociated.commons.correlationid.base.ThreadCorrelationId;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.RecordInterceptor;

import static com.xcodeassociated.commons.correlationid.base.CorrelationIdConstants.CORRELATION_ID_KEY;

import java.util.stream.StreamSupport;

public abstract class KafkaRecordInterceptor<K, V> implements RecordInterceptor<K, V> {

    private ThreadCorrelationId threadCorrelationId;

    public KafkaRecordInterceptor(ThreadCorrelationId threadCorrelationId) {
        this.threadCorrelationId = threadCorrelationId;
    }

    @Override
    public ConsumerRecord<K, V> intercept(ConsumerRecord<K, V> record) {
        setCorrelationId(record);
        return record;
    }

    private void setCorrelationId(ConsumerRecord<K, V> record) {
        StreamSupport.stream(record.headers().spliterator(), false)
            .filter(p -> CORRELATION_ID_KEY.equals(p.key()))
            .map(p -> new String(p.value()))
            .findFirst()
            .map(p -> {
                threadCorrelationId.setCorrelationId(p);
                return p;
            }).orElseGet(() -> threadCorrelationId.createNewCorrelationId());
    }

}
