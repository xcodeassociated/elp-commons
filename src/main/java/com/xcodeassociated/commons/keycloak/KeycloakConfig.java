package com.xcodeassociated.commons.keycloak;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@ComponentScan("com.xcodeassociated.commons.keycloak")
@Configuration
public class KeycloakConfig {

    @Bean
    public TokenApi tokenApi(@Value("${external.token.api.url}") String url, ObjectMapper mapper) {
        return new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build()
                .create(TokenApi.class);
    }

}
