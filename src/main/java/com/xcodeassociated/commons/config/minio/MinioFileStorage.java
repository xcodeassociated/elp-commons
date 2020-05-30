package com.xcodeassociated.commons.config.minio;

import io.minio.MinioClient;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class MinioFileStorage {

    private final MinioClient minioClient;

    public Try<String> upload(String objectName, String bucketName, InputStream inputStream, MediaType mediaType) {
        return Try.ofCallable(() -> uploadObject(objectName, bucketName, inputStream, mediaType.toString()));
    }

    public Try<String> upload(String objectName, String bucketName, String json) {
        return Try.ofCallable(() -> uploadObject(objectName, bucketName, new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)), MediaType.APPLICATION_JSON_UTF8.toString()));
    }

    public Try<String> upload(String objectName, String bucketName, byte[] bytes, MediaType mediaType) {
        return Try.ofCallable(() -> uploadObject(objectName, bucketName, new ByteArrayInputStream(bytes), mediaType.toString()));
    }

    public Try<String> upload(String objectName, String bucketName, InputStream inputStream, String mediaType) {
        return Try.ofCallable(() -> uploadObject(objectName, bucketName, inputStream, mediaType));
    }

    public Try<InputStream> getObject(String bucketName, String objectName) {
        return Try.ofCallable(() -> minioClient.getObject(bucketName, objectName));
    }

    private String uploadObject(String objectName, String bucketName, InputStream inputStream, String mediaType) throws Exception {
        if (!minioClient.bucketExists(bucketName)) {
            minioClient.makeBucket(bucketName);
            minioClient.setBucketPolicy(bucketName, String.format("{\n" +
                    "  \"Version\":\"2012-10-17\",\n" +
                    "  \"Statement\":[\n" +
                    "    {\n" +
                    "      \"Sid\":\"AddPerm\",\n" +
                    "      \"Effect\":\"Allow\",\n" +
                    "      \"Principal\": \"*\",\n" +
                    "      \"Action\":[\"s3:GetObject\"],\n" +
                    "      \"Resource\":[\"arn:aws:s3:::%s/*\"]\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}", bucketName));
        }
        minioClient.putObject(bucketName, objectName, inputStream, mediaType);
        return minioClient.getObjectUrl(bucketName, objectName);
    }

    public Try<String> getObjectUrl(String objectName, String bucketName) {
        return Try.ofCallable(() -> minioClient.getObjectUrl(objectName, bucketName));
    }

}
