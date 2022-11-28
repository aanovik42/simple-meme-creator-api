package com.aanovik42.smartmemecreatorapi.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class MemeRequestDto {

    @NotNull(message = "templateId cannot be null")
    private Long templateId;

    @NotNull(message = "text cannot be null")
    private List<
            @NotNull(message = "A line of text cannot be null")
            String> text;
}
