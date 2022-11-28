package com.aanovik42.smartmemecreatorapi.component;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Component
public class FilenameCreatorUuid implements FilenameCreator {

    private final String memeImagePrefix;

    public FilenameCreatorUuid(@Value("${storage.meme-image-prefix}") String memeImagePrefix) {
        this.memeImagePrefix = memeImagePrefix;
    }

    @Override
    public String createFileName() {

        return memeImagePrefix + UUID.randomUUID() + ".png";
    }
}
