package com.xcodeassociated.commons.web;

import com.xcodeassociated.commons.config.security.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(SecurityConfig.ALLOWED_METHODS.stream().toArray(String[]::new));
    }
}
