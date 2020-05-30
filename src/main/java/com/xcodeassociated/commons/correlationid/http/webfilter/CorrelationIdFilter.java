package com.xcodeassociated.commons.correlationid.http.webfilter;

import com.xcodeassociated.commons.correlationid.base.CorrelationIdNotFoundException;
import com.xcodeassociated.commons.correlationid.base.ThreadCorrelationId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;

@Slf4j
@Component
public class CorrelationIdFilter implements Filter {

    private final ThreadCorrelationId threadCorrelationId;

    public CorrelationIdFilter(ThreadCorrelationId threadCorrelationId) {
        this.threadCorrelationId = threadCorrelationId;
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        try {
            setCorrelationIdHeader(servletResponse, extractCorrelationIdHeader(httpServletRequest));
            filterChain.doFilter(httpServletRequest, servletResponse);
        } catch(CorrelationIdNotFoundException e) {
            log.warn("No correlation id", e);
        }
    }

    private String extractCorrelationIdHeader(HttpServletRequest httpServletRequest) {
        String correlationId = httpServletRequest.getHeader(ThreadCorrelationId.CORRELATION_ID_HEADER);
        if(!currentRequestIsAsyncDispatcher(httpServletRequest)) {
            if(isNull(correlationId)) {
                correlationId = threadCorrelationId.createNewCorrelationId();
            } else {
                threadCorrelationId.setCorrelationId(correlationId);
            }
        }
        return correlationId;
    }

    private void setCorrelationIdHeader(ServletResponse servletResponse, String correlationId) {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if(response.containsHeader(ThreadCorrelationId.CORRELATION_ID_HEADER)) {
            response.setHeader(ThreadCorrelationId.CORRELATION_ID_HEADER, correlationId);
        } else {
            response.addHeader(ThreadCorrelationId.CORRELATION_ID_HEADER, correlationId);
        }
    }

    private boolean currentRequestIsAsyncDispatcher(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getDispatcherType().equals(DispatcherType.ASYNC);
    }

}
