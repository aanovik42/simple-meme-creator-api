package com.aanovik42.smartmemecreatorapi.controller.modelassembler;

import com.aanovik42.smartmemecreatorapi.controller.MemeController;
import com.aanovik42.smartmemecreatorapi.controller.MemeTemplateController;
import com.aanovik42.smartmemecreatorapi.controller.dto.RootEntryPointResponseDto;
import lombok.SneakyThrows;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class RootEntryPointModelAssembler implements
        RepresentationModelAssembler<RootEntryPointResponseDto, EntityModel<RootEntryPointResponseDto>> {
    @SneakyThrows
    @Override
    public EntityModel<RootEntryPointResponseDto> toModel(RootEntryPointResponseDto rootEntryPointResponseDto) {

        return EntityModel.of(rootEntryPointResponseDto,
                WebMvcLinkBuilder.linkTo(methodOn(MemeTemplateController.class).getMemeTemplate(null)).withRel("templates"),
                WebMvcLinkBuilder.linkTo(methodOn(MemeController.class).createMeme(null)).withRel("memes"));
    }
}
