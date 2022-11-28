package com.aanovik42.smartmemecreatorapi.controller;

import com.aanovik42.smartmemecreatorapi.entity.Meme;
import com.aanovik42.smartmemecreatorapi.controller.dto.MemeRequestDto;
import com.aanovik42.smartmemecreatorapi.controller.dto.MemeResponseDto;
import com.aanovik42.smartmemecreatorapi.service.MemeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.*;
import java.io.IOException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/memes")
public class MemeController {

    private final MemeService memeService;
    private final ModelMapper modelMapper;

    public MemeController(MemeService memeService, ModelMapper modelMapper) {
        this.memeService = memeService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<String> createMeme(@Valid @RequestBody MemeRequestDto requestDto) throws IOException, FontFormatException {

        Meme meme = memeService.createMeme(requestDto.getTemplateId(), requestDto.getText());
        MemeResponseDto responseDto = modelMapper.map(meme, MemeResponseDto.class);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, responseDto.getUrl())
                .build();
    }
}