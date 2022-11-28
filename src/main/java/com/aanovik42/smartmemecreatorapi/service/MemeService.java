package com.aanovik42.smartmemecreatorapi.service;

import com.aanovik42.smartmemecreatorapi.entity.Meme;
import com.aanovik42.smartmemecreatorapi.entity.MemeTemplate;
import com.aanovik42.smartmemecreatorapi.exception.MemeTemplateIdUnprocessableException;
import com.aanovik42.smartmemecreatorapi.exception.TextSizeException;
import com.aanovik42.smartmemecreatorapi.memecreator.MemeCanvas;
import com.aanovik42.smartmemecreatorapi.repository.MemeRepository;
import com.aanovik42.smartmemecreatorapi.repository.MemeTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@Service
public class MemeService {

    private final StorageService storageService;
    private final MemeTemplateRepository memeTemplateRepository;
    private final MemeRepository memeRepository;
    private final MemeCanvas memeCanvas;

    public MemeService(StorageService storageService, MemeTemplateRepository memeTemplateRepository,
                       MemeRepository memeRepository, MemeCanvas memeCanvas) {
        this.storageService = storageService;
        this.memeTemplateRepository = memeTemplateRepository;
        this.memeRepository = memeRepository;
        this.memeCanvas = memeCanvas;
    }

    @Transactional
    public Meme createMeme(Long templateId, List<String> text) throws IOException, FontFormatException {

        MemeTemplate memeTemplate = memeTemplateRepository.findById(templateId).orElseThrow(() -> new MemeTemplateIdUnprocessableException(templateId));

        int boxCount = memeTemplate.getBoxCount();
        if (boxCount != text.size())
            throw new TextSizeException(boxCount);
        BufferedImage memeTemplateImage = storageService.loadMemeTemplateImage(memeTemplate.getTemplateImage());

        BufferedImage memeImage = memeCanvas.createMemeImage(memeTemplate, text, memeTemplateImage);
        String filename = storageService.saveMemeImage(memeImage);
        Meme meme = new Meme(filename);

        return memeRepository.save(meme);
    }

}
