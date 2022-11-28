package com.aanovik42.smartmemecreatorapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemeTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String templateImage;
    private String sampleImage;
    private int width;
    private int height;
    private int boxCount;
    private String fontName;
    private int maxFontSize;
    @OneToMany(mappedBy = "memeTemplate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<TextBoxTemplate> textBoxTemplates;

    public MemeTemplate(String name, String templateImage, String sampleImage,
                        int width, int height, int boxCount, List<TextBoxTemplate> textBoxTemplates, String fontName) {
        this.name = name;
        this.templateImage = templateImage;
        this.sampleImage = sampleImage;
        this.width = width;
        this.height = height;
        this.boxCount = boxCount;
        this.textBoxTemplates = textBoxTemplates;
        this.fontName = fontName;
    }
}
