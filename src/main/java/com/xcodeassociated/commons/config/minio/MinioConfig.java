package com.xcodeassociated.commons.config.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Bean
    public MinioFileStorage minioFileStorage(MinioClient minioClient) {
        return new MinioFileStorage(minioClient);
    }

    @Bean
    public MinioClient minioClient(@Value("${minio.endpoint}") String minioEndpoint,
                                   @Value("${minio.keys.access}") String minioAccessKey,
                                   @Value("${minio.keys.secret}") String minioSecretKey) throws Exception {
        return new MinioClient(minioEndpoint, minioAccessKey, minioSecretKey);
    }

}
