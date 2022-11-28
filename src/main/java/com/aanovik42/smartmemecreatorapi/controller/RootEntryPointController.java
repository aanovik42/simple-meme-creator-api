package com.aanovik42.smartmemecreatorapi.controller;

import com.aanovik42.smartmemecreatorapi.controller.dto.RootEntryPointResponseDto;
import com.aanovik42.smartmemecreatorapi.controller.modelassembler.RootEntryPointModelAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class RootEntryPointController {

    private final RootEntryPointModelAssembler rootEntryPointModelAssembler;

    public RootEntryPointController(RootEntryPointModelAssembler rootEntryPointModelAssembler) {
        this.rootEntryPointModelAssembler = rootEntryPointModelAssembler;
    }

    @GetMapping
    public EntityModel<RootEntryPointResponseDto> getRoot() {

        return rootEntryPointModelAssembler.toModel(new RootEntryPointResponseDto());
    }
}
