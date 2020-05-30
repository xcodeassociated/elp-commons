package com.xcodeassociated.commons.correlationid.http.retrofit;

import com.xcodeassociated.commons.correlationid.base.ThreadCorrelationId;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationIdInterceptor implements Interceptor {

    private static final String REQUEST_ID_HEADER_NAME = "X-Request-ID";

    private final ThreadCorrelationId threadCorrelationId;

    public CorrelationIdInterceptor(ThreadCorrelationId threadCorrelationId) {
        this.threadCorrelationId = threadCorrelationId;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader(ThreadCorrelationId.CORRELATION_ID_HEADER, threadCorrelationId.getOrCreateCorrelationId())
                .addHeader(REQUEST_ID_HEADER_NAME, UUID.randomUUID().toString())
                .build();
        return chain.proceed(request);
    }

}
