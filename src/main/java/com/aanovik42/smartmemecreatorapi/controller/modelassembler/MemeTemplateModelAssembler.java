package com.aanovik42.smartmemecreatorapi.controller.modelassembler;

import com.aanovik42.smartmemecreatorapi.controller.MemeTemplateController;
import com.aanovik42.smartmemecreatorapi.controller.dto.MemeTemplateResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MemeTemplateModelAssembler implements
        RepresentationModelAssembler<MemeTemplateResponseDto, EntityModel<MemeTemplateResponseDto>> {

    @Override
    public EntityModel<MemeTemplateResponseDto> toModel(MemeTemplateResponseDto memeTemplateResponseDto) {

        return EntityModel.of(memeTemplateResponseDto,
                linkTo(methodOn(MemeTemplateController.class).getMemeTemplate(memeTemplateResponseDto.getId())).withSelfRel(),
                linkTo(methodOn(MemeTemplateController.class).getAllMemeTemplates()).withRel("templates"));
    }
}
