package com.aanovik42.smartmemecreatorapi.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
public class UrlCreatorS3 implements UrlCreator {

    private final String s3Url;
    private final String s3BucketName;

    public UrlCreatorS3(@Value("${storage.s3.url}") String s3Url,
                        @Value("${storage.s3.bucket-name}") String s3BucketName) {
        this.s3Url = s3Url;
        this.s3BucketName = s3BucketName;
    }

    @Override
    public String createMemeImageURL(String filename) {
        return s3Url + s3BucketName + "/" + filename;
    }

    @Override
    public String createTemplateImageURL(String filename) {
        return s3Url + s3BucketName + "/" + filename;
    }
}
