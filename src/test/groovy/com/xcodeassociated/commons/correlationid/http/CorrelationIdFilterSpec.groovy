package com.xcodeassociated.commons.correlationid.http

import com.fasterxml.jackson.databind.ObjectMapper
import com.xcodeassociated.commons.correlationid.base.ThreadCorrelationId
import com.xcodeassociated.commons.correlationid.http.webfilter.CorrelationIdFilter
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import spock.lang.Subject

import javax.servlet.DispatcherType
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ActiveProfiles("test")
class CorrelationIdFilterSpec extends Specification {

    String errorPayload = "error"

    ThreadCorrelationId threadCorrelationId = Mock(ThreadCorrelationId)
    ObjectMapper objectMapper = Mock(ObjectMapper)
    FilterChain filterChain = Mock(FilterChain)
    HttpServletRequest request = Mock(HttpServletRequest)
    HttpServletResponse response = Mock(HttpServletResponse)

    @Subject
    CorrelationIdFilter correlationIdFilter

    def setup() {
        request.getDispatcherType() >> DispatcherType.REQUEST
        objectMapper.writeValueAsString(_) >> errorPayload
        correlationIdFilter = new CorrelationIdFilter(threadCorrelationId)
    }

    def "Should pass along a request when correlation ID set"() {
        given:
            request.getHeader(ThreadCorrelationId.CORRELATION_ID_HEADER) >> "correlationID"
        when:
            correlationIdFilter.doFilter(request, response, filterChain)
        then:
            1 * filterChain.doFilter(request, response)
    }

    def "Should generate correlation ID and pass along a request when correlation ID is not set"() {
        given:
            request.getHeader(ThreadCorrelationId.CORRELATION_ID_HEADER) >> null
            request.getRequestURI() >> "/api/icrm/xyz"
        when:
            correlationIdFilter.doFilter(request, response, filterChain)
        then:
            1 * threadCorrelationId.createNewCorrelationId()
            1 * filterChain.doFilter(request, response)
    }
}
