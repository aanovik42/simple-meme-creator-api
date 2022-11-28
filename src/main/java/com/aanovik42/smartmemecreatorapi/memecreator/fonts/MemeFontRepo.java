package com.aanovik42.smartmemecreatorapi.memecreator.fonts;

import com.aanovik42.smartmemecreatorapi.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class MemeFontRepo {

    private final StorageService storageService;
    private final Map<String, Font> fonts;
    public final Integer MIN_FONT_SIZE;
    public final Integer FONT_SIZE_STEP;

    public MemeFontRepo(StorageService storageService,
                        @Value("${font.size.min}") Integer minFontSize,
                        @Value("${font.size.step}") Integer fontSizeStep) {
        fonts = new HashMap<>();
        this.storageService = storageService;
        this.MIN_FONT_SIZE = minFontSize;
        this.FONT_SIZE_STEP = fontSizeStep;
    }


    public Font getFont(String fontName) throws IOException, FontFormatException {

        if(fonts.containsKey(fontName))
            return fonts.get(fontName);

        InputStream fontStream = storageService.loadFontFromFile(fontName);
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
        fonts.put(fontName, font);

        return font;
    }
}
