package com.xcodeassociated.commons.envers;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.xcodeassociated.commons.envers")
@EntityScan("com.xcodeassociated.commons.envers")
public class EnversConfig {
}
