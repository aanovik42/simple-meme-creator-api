package com.aanovik42.smartmemecreatorapi.service;

import com.aanovik42.smartmemecreatorapi.component.FilenameCreator;
import com.aanovik42.smartmemecreatorapi.component.ImageToByteArrayConverter;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class S3StorageService extends StorageService {

    private final AmazonS3 s3;
    private final String s3BucketName;
    private final ImageToByteArrayConverter imageToByteArrayConverter;
    private final FilenameCreator filenameCreator;

    public S3StorageService(AmazonS3 s3,
                            @Value("${storage.s3.bucket-name}") String s3BucketName,
                            ImageToByteArrayConverter imageToByteArrayConverter,
                            FilenameCreator filenameCreator,
                            @Value("${font.dir}") String fontsLocalDir) {
        super(fontsLocalDir);
        this.s3 = s3;
        this.s3BucketName = s3BucketName;
        this.imageToByteArrayConverter = imageToByteArrayConverter;
        this.filenameCreator = filenameCreator;
    }

    @Override
    BufferedImage loadMemeTemplateImage(String filename) throws IOException {

        S3Object object = s3.getObject(s3BucketName, filename);
        S3ObjectInputStream objectContent = object.getObjectContent();
        BufferedImage image = ImageIO.read(objectContent);

        return image;
    }

    @Override
    public String saveMemeImage(BufferedImage memeImage) throws IOException {

        String filename = filenameCreator.createFileName();

        byte[] buffer = imageToByteArrayConverter.convertBufferedImageToByteArray(memeImage, "png");
        InputStream inputStream = new ByteArrayInputStream(buffer);

        ObjectMetadata metaData = new ObjectMetadata();
        metaData.setContentLength(buffer.length);
        metaData.setContentType("image/png");

        s3.putObject(s3BucketName, filename, inputStream, metaData);

        return filename;
    }
}
