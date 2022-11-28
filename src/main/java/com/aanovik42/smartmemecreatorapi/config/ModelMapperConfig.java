package com.aanovik42.smartmemecreatorapi.config;

import com.aanovik42.smartmemecreatorapi.entity.Meme;
import com.aanovik42.smartmemecreatorapi.entity.MemeTemplate;
import com.aanovik42.smartmemecreatorapi.controller.dto.MemeResponseDto;
import com.aanovik42.smartmemecreatorapi.controller.dto.MemeTemplateResponseDto;
import com.aanovik42.smartmemecreatorapi.component.UrlCreator;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    private final UrlCreator urlCreator;

    public ModelMapperConfig(UrlCreator urlCreator) {
        this.urlCreator = urlCreator;
    }

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        // Combine s3-url, s3-bucket and filename to MemeResponseModel.url
        modelMapper.createTypeMap(Meme.class, MemeResponseDto.class)
                .addMappings(
                        new PropertyMap<>() {
                            @Override
                            protected void configure() {

                                using(el -> urlCreator.createMemeImageURL(
                                        ((Meme) el.getSource()).getFilename()))
                                        .map(source, destination.getUrl());
                            }
                        });

        modelMapper.createTypeMap(MemeTemplate.class, MemeTemplateResponseDto.class)
                .addMappings(
                        new PropertyMap<>() {
                            @Override
                            protected void configure() {

                                using(el -> urlCreator.createTemplateImageURL(
                                        ((MemeTemplate) el.getSource()).getTemplateImage()))
                                        .map(source, destination.getTemplateImageUrl());
                                using(el -> urlCreator.createTemplateImageURL(
                                        ((MemeTemplate) el.getSource()).getSampleImage()))
                                        .map(source, destination.getSampleImageUrl());
                            }
                        });

        return modelMapper;
    }
}
