package com.aanovik42.smartmemecreatorapi.service;

import com.aanovik42.smartmemecreatorapi.entity.MemeTemplate;
import com.aanovik42.smartmemecreatorapi.exception.MemeTemplateIdNotFoundException;
import com.aanovik42.smartmemecreatorapi.repository.MemeTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemeTemplateService {

    private final MemeTemplateRepository memeTemplateRepository;

    public MemeTemplateService(MemeTemplateRepository memeTemplateRepository) {
        this.memeTemplateRepository = memeTemplateRepository;
    }


    public List<MemeTemplate> getAllMemeTemplates() {
        return memeTemplateRepository.findAll();
    }

    public MemeTemplate getMemeTemplate(Long id) {

        return memeTemplateRepository.findById(id).orElseThrow(() -> new MemeTemplateIdNotFoundException(id));
    }
}
