package com.aanovik42.smartmemecreatorapi.entity;

import com.aanovik42.smartmemecreatorapi.entity.enums.TextAlign;
import com.aanovik42.smartmemecreatorapi.entity.enums.VerticalAlign;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TextBoxTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int positionTop;
    private int positionLeft;
    private int width;
    private int height;
    private String color;
    private TextAlign textAlign;
    private VerticalAlign verticalAlign;
    private boolean uppercase;
    private boolean shadow;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meme_template_id")
    @JsonIgnore
    private MemeTemplate memeTemplate;

}