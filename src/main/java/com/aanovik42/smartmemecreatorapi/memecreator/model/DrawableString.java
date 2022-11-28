package com.aanovik42.smartmemecreatorapi.memecreator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DrawableString {

    private List<DrawableTextLine> textLines;
    private int fontSize;
    private int textHeight;
    private boolean shaded;
}
