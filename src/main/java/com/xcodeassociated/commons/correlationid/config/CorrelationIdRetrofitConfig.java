package com.xcodeassociated.commons.correlationid.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.xcodeassociated.commons.correlationid.base", "com.xcodeassociated.commons.correlationid.http.retrofit"})
public class CorrelationIdRetrofitConfig {

}
