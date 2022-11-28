package com.aanovik42.smartmemecreatorapi.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "templates")
public class MemeTemplateResponseDto {

    private Long id;
    private String name;
    private String templateImageUrl;
    private String sampleImageUrl;
    private int width;
    private int height;
    private int boxCount;
}
