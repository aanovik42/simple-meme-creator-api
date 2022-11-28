package com.aanovik42.smartmemecreatorapi.component;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class ImageToByteArrayConverter {

    public byte[] convertBufferedImageToByteArray(BufferedImage image, String formatName) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, formatName, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
