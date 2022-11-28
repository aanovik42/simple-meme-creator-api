package com.aanovik42.smartmemecreatorapi.controller;


import com.aanovik42.smartmemecreatorapi.controller.dto.MemeTemplateResponseDto;
import com.aanovik42.smartmemecreatorapi.controller.modelassembler.MemeTemplateModelAssembler;
import com.aanovik42.smartmemecreatorapi.entity.MemeTemplate;
import com.aanovik42.smartmemecreatorapi.service.MemeTemplateService;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/templates")
public class MemeTemplateController {

    private final MemeTemplateService memeTemplateService;
    private final MemeTemplateModelAssembler memeTemplateModelAssembler;
    private final ModelMapper modelMapper;

    public MemeTemplateController(MemeTemplateService memeTemplateService, MemeTemplateModelAssembler memeTemplateModelAssembler, ModelMapper modelMapper) {
        this.memeTemplateService = memeTemplateService;
        this.memeTemplateModelAssembler = memeTemplateModelAssembler;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public CollectionModel<EntityModel<MemeTemplateResponseDto>> getAllMemeTemplates() {

        List<MemeTemplate> memeTemplates = memeTemplateService.getAllMemeTemplates();

        List<MemeTemplateResponseDto> responseDto = memeTemplates.stream()
                .map(el -> modelMapper.map(el, MemeTemplateResponseDto.class))
                .collect(Collectors.toList());

        List<EntityModel<MemeTemplateResponseDto>> responseModel = responseDto.stream()
                .map(memeTemplateModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(responseModel,
                linkTo(methodOn(MemeTemplateController.class).getAllMemeTemplates()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<MemeTemplateResponseDto> getMemeTemplate(@PathVariable Long id) {

        MemeTemplate memeTemplate = memeTemplateService.getMemeTemplate(id);
        MemeTemplateResponseDto responseDto = modelMapper.map(memeTemplate, MemeTemplateResponseDto.class);

        return memeTemplateModelAssembler.toModel(responseDto);
    }
}
