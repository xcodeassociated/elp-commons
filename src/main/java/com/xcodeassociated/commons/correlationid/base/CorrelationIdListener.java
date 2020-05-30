package com.xcodeassociated.commons.correlationid.base;

public interface CorrelationIdListener {

    void notify(String correlationId);

    void clear();

}
