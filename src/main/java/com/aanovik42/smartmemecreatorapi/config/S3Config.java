package com.aanovik42.smartmemecreatorapi.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    private final String accessKey;
    private final String secretKey;
    private final String serviceEndpoint;
    private final String signingRegion;

    public S3Config(@Value("${storage.s3.access-key}") String accessKey,
                    @Value("${storage.s3.secret-key}") String secretKey,
                    @Value("${storage.s3.service-endpoint}") String serviceEndpoint,
                    @Value("${storage.s3.signing-region}") String signingRegion) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.serviceEndpoint = serviceEndpoint;
        this.signingRegion = signingRegion;
    }

    @Bean
    public AmazonS3 s3() {
        AWSCredentials awsCredentials =
                new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration(
                                serviceEndpoint, signingRegion
                        )
                )
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
