package com.aanovik42.smartmemecreatorapi.memecreator;

import com.aanovik42.smartmemecreatorapi.memecreator.model.DrawableString;
import com.aanovik42.smartmemecreatorapi.memecreator.model.DrawableTextLine;
import com.jhlabs.image.GaussianFilter;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.util.List;

@Component
public class ShadowCanvas {

    private final GaussianFilter gaussianFilter;

    public ShadowCanvas(GaussianFilter gaussianFilter) {
        this.gaussianFilter = gaussianFilter;
    }

    public BufferedImage getShadowLayer(List<DrawableString> drawableStrings, int width, int height) {

        BufferedImage shadowLayer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = shadowLayer.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        drawableStrings.forEach(str -> {
            if(str.isShaded()) drawShadow(str, g2d);
        });

        gaussianFilter.filter(shadowLayer, shadowLayer);
        return shadowLayer;
    }

    private void drawShadow(DrawableString drawableString, Graphics2D g2d) {

        g2d.setColor(Color.BLACK);

        List<DrawableTextLine> textLines = drawableString.getTextLines();

        for (DrawableTextLine textLine : textLines) {
            TextLayout line = textLine.getLine();
            Shape outline = line.getOutline(null);
            float x = textLine.getX();
            float y = textLine.getY();
            g2d.translate(x, y);
            g2d.draw(outline);
            g2d.translate(-x, -y);
        }
    }
}
