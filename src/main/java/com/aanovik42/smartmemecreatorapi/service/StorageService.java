package com.aanovik42.smartmemecreatorapi.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public abstract class StorageService {

    private String fontsLocalDir;

    protected StorageService(String fontsLocalDir) {
        this.fontsLocalDir = fontsLocalDir;
    }

    abstract BufferedImage loadMemeTemplateImage(String filename) throws IOException;
    abstract String saveMemeImage(BufferedImage memeImage) throws IOException;

    public InputStream loadFontFromFile(String fontName) throws IOException {

        String path = fontsLocalDir + fontName + ".ttf";
        return loadFileFromResources(path);
    }

    private InputStream loadFileFromResources(String path) throws IOException {

        Resource resource = new ClassPathResource(path);
        return resource.getInputStream();
    }
}
