package com.xcodeassociated.commons.correlationid.kafka;

import static com.xcodeassociated.commons.correlationid.base.CorrelationIdConstants.CORRELATION_ID_KEY;

import java.util.Map;

import com.xcodeassociated.commons.correlationid.base.ThreadCorrelationId;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.internals.RecordHeader;

public class KafkaProducerInterceptor implements ProducerInterceptor<String, Object> {

    private ThreadCorrelationId threadCorrelationId;

    @Override
    public ProducerRecord<String, Object> onSend(ProducerRecord<String, Object> producerRecord) {
        String correlationId = threadCorrelationId.getOrCreateCorrelationId();
        producerRecord.headers().add(new RecordHeader(CORRELATION_ID_KEY, correlationId.getBytes()));
        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
    }

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> configs) {
        this.threadCorrelationId = (ThreadCorrelationId) configs.get(ThreadCorrelationId.class.getSimpleName());
    }

}
