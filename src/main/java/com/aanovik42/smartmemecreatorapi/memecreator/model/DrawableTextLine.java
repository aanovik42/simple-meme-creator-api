package com.aanovik42.smartmemecreatorapi.memecreator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.font.TextLayout;

@Getter
@Setter
@AllArgsConstructor
public class DrawableTextLine {

    private TextLayout line;
    private float x;
    private float y;
}
