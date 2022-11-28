package com.aanovik42.smartmemecreatorapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    private Date createdAt;

    public Meme(String filename) {
        this.filename = filename;
    }

    @PrePersist
    public void onCreate() {
        createdAt = new Date();
    }
}
