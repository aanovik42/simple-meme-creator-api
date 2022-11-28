package com.aanovik42.smartmemecreatorapi.memecreator;

import com.aanovik42.smartmemecreatorapi.entity.MemeTemplate;
import com.aanovik42.smartmemecreatorapi.entity.enums.TextAlign;
import com.aanovik42.smartmemecreatorapi.entity.TextBoxTemplate;
import com.aanovik42.smartmemecreatorapi.entity.enums.VerticalAlign;
import com.aanovik42.smartmemecreatorapi.exception.TextLineTooLongException;
import com.aanovik42.smartmemecreatorapi.memecreator.fonts.MemeFontRepo;
import com.aanovik42.smartmemecreatorapi.memecreator.model.DrawableString;
import com.aanovik42.smartmemecreatorapi.memecreator.model.DrawableTextLine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class MemeCanvas {

    private Font font;
    private final MemeFontRepo memeFontRepo;
    private final ShadowCanvas shadowCanvas;
    private final int LINE_TOO_SHORT_DIVIDER;

    public MemeCanvas(MemeFontRepo memeFontRepo, ShadowCanvas shadowCanvas,
                      @Value("${line.too-short.divider}") int lineTooShortDivider) {
        this.memeFontRepo = memeFontRepo;
        this.shadowCanvas = shadowCanvas;
        LINE_TOO_SHORT_DIVIDER = lineTooShortDivider > 0 ? lineTooShortDivider : 1;
    }

    public BufferedImage createMemeImage(MemeTemplate memeTemplate, List<String> text, BufferedImage image) throws IOException, FontFormatException {

        // Prepare the canvas
        String fontName = memeTemplate.getFontName();
        font = memeFontRepo.getFont(fontName).deriveFont((float) memeTemplate.getMaxFontSize());
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Replace empty strings with spaces as "empty" characters (mandatory for AttributedString)
        text = text.stream()
                .map(textLine -> textLine.isEmpty() ? " " : textLine)
                .collect(Collectors.toList());

        // Calculate font size
        List<TextBoxTemplate> textBoxTemplates = memeTemplate.getTextBoxTemplates();
        List<String> formattedText = textToUppercase(text, textBoxTemplates);
        decideOnFontSize(formattedText, textBoxTemplates, g2d);

        // Get ready-to-draw graphical representation of the strings, each split into several parts if needed
        List<DrawableString> drawableStrings = new ArrayList<>();
        for (int i = 0; i < formattedText.size(); i++) {
            TextBoxTemplate textBoxTemplate = textBoxTemplates.get(i);
            String string = formattedText.get(i);
            DrawableString drawableString = getDrawableString(string, textBoxTemplate, g2d);
            drawableStrings.add(drawableString);
        }

        // Draw shadows if needed
        for (DrawableString drawableString : drawableStrings) {
            if (drawableString.isShaded()) {
                BufferedImage shadowLayer = shadowCanvas.getShadowLayer(drawableStrings, image.getWidth(), image.getHeight());
                g2d.drawImage(shadowLayer, null, 0, 0);
                break;
            }
        }

        // Draw text
        for (int i = 0; i < drawableStrings.size(); i++) {
            TextBoxTemplate textBoxTemplate = textBoxTemplates.get(i);
            DrawableString drawableString = drawableStrings.get(i);
            drawString(textBoxTemplate, drawableString, g2d);
        }

        return image;
    }

    private List<String> textToUppercase(List<String> text, List<TextBoxTemplate> textBoxTemplates) {

        List<String> formattedText = new ArrayList<>();

        for (int i = 0; i < text.size(); i++) {
            TextBoxTemplate textBoxTemplate = textBoxTemplates.get(i);
            String string = text.get(i);
            if (textBoxTemplate.isUppercase())
                formattedText.add(string.toUpperCase(Locale.getDefault()));
            else
                formattedText.add(string);
        }

        return formattedText;
    }

    private void drawString(TextBoxTemplate textBoxTemplate, DrawableString drawableString, Graphics2D g2d) {
        g2d.setColor(Color.decode(textBoxTemplate.getColor()));

        List<DrawableTextLine> textLines = drawableString.getTextLines();

        for (DrawableTextLine textLine : textLines) {
            TextLayout line = textLine.getLine();
            float x = textLine.getX();
            float y = textLine.getY();
            line.draw(g2d, x, y);
        }
    }

    private void decideOnFontSize(List<String> text, List<TextBoxTemplate> textBoxTemplates, Graphics2D g2d) {

        for (int i = 0; i < text.size(); i++) {
            String string = text.get(i);
            TextBoxTemplate textBoxTemplate = textBoxTemplates.get(i);
            List<TextLayout> lines;
            int fontSize = font.getSize();
            int textHeight;
            do {
                if (fontSize < memeFontRepo.MIN_FONT_SIZE)
                    throw new TextLineTooLongException(i, string);

                font = font.deriveFont((float) fontSize);
                g2d.setFont(font);

                lines = splitStringIntoPiecesToFitGivenWidth(string, textBoxTemplate.getWidth(), g2d);

                textHeight = getTextHeight(lines);
                if (textHeight / textBoxTemplate.getHeight() > 2)
                    fontSize /= 2;
                else
                    fontSize -= memeFontRepo.FONT_SIZE_STEP;

            } while (textHeight >= textBoxTemplate.getHeight());
        }
    }

    /**
     * After splitting, we could get 2 or more text lines
     * Sometimes the last line looks too short compared to other lines, which feels pretty ugly
     * This method checks the line width and compares it to the "normal" width
     * LINE_TOO_SHORT_DIVIDER was chosen experimentally
     */
    private boolean checkIfLastLineIsTooShort(List<TextLayout> lines, int textBoxTemplateWidth) {

        double normalWidth = (double) textBoxTemplateWidth / LINE_TOO_SHORT_DIVIDER;
        int size = lines.size();
        boolean lastLineIsTooShort = false;
        if (size > 1) {
            TextLayout lastLine = lines.get(size - 1);
            double lastLineWidth = lastLine.getBounds().getWidth();
            if (lastLineWidth < normalWidth)
                lastLineIsTooShort = true;
        }

        return lastLineIsTooShort;
    }

    private DrawableString getDrawableString(String string, TextBoxTemplate textBoxTemplate, Graphics2D g2d) {

        List<TextLayout> lines;
        /*
        First of all, we should check the width of the last line.
        If it's too small (which looks pretty ugly), we'll try to "reduce" the width of text box
        This will make LineBreakMeasurer to create different layouts
        As a result, lines will look more equal to each other
         */
        boolean lastLineIsTooShort;
        int textBoxWidth = textBoxTemplate.getWidth();
        double widthMultiplier = 1.0;
        double lastLineEmptySpaceReserve = 1.0 - (widthMultiplier / LINE_TOO_SHORT_DIVIDER);
        double stolenFromLastLine;
        do {
            lines = splitStringIntoPiecesToFitGivenWidth(string, (int) (textBoxWidth * widthMultiplier), g2d);
            lastLineIsTooShort = checkIfLastLineIsTooShort(lines, (int) (textBoxWidth * widthMultiplier));
            widthMultiplier -= 0.05;
            stolenFromLastLine = (1.0 - widthMultiplier) * lines.size();
        } while (lastLineIsTooShort && stolenFromLastLine < lastLineEmptySpaceReserve);

        /*
        And here we go with some calculations for Graphics2D
         */
        int textHeight = getTextHeight(lines);

        List<DrawableTextLine> textLines = new ArrayList<>();
        float y = calculateY(textBoxTemplate, textHeight);

        for (TextLayout line : lines) {
            Rectangle2D bounds = line.getBounds();
            double lineWidth = bounds.getWidth();
            float x = calculateX(textBoxTemplate, lineWidth);
            textLines.add(new DrawableTextLine(line, x, y + line.getAscent()));

            y += line.getAscent() + line.getDescent() + line.getLeading();
        }

        boolean shadow = textBoxTemplate.isShadow();

        return new DrawableString(textLines, font.getSize(), textHeight, shadow);
    }

    private int getTextHeight(List<TextLayout> lines) {

        int textHeight = 0;
        for (TextLayout line : lines) {
            textHeight += line.getAscent() + line.getDescent() + line.getLeading();
        }

        return textHeight;
    }

    private float calculateX(TextBoxTemplate textBoxTemplate, double lineWidth) {

        float x = textBoxTemplate.getPositionLeft();
        TextAlign textAlign = textBoxTemplate.getTextAlign();

        switch (textAlign) {
            case CENTER:
                x += (textBoxTemplate.getWidth() - lineWidth) / 2;
                break;
            case RIGHT:
                x += textBoxTemplate.getWidth() - lineWidth;
                break;
        }

        return x;
    }

    private float calculateY(TextBoxTemplate textBoxTemplate, int textHeight) {

        float y;
        VerticalAlign verticalAlign = textBoxTemplate.getVerticalAlign();

        switch (verticalAlign) {
            case TOP:
                y = textBoxTemplate.getPositionTop();
                break;
            case BOTTOM:
                y = (textBoxTemplate.getHeight() - textHeight) + textBoxTemplate.getPositionTop();
                break;
            default:
                y = (float) (textBoxTemplate.getHeight() - textHeight) / 2 + textBoxTemplate.getPositionTop();
        }

        return y;
    }

    private List<TextLayout> splitStringIntoPiecesToFitGivenWidth(String string, int width, Graphics2D g2d) {
        List<TextLayout> lines = new ArrayList<>();

        AttributedString attributedString = new AttributedString(string);
        attributedString.addAttribute(TextAttribute.FONT, font);
        LineBreakMeasurer lineBreakMeasurer = new LineBreakMeasurer(attributedString.getIterator(), g2d.getFontRenderContext());

        while (lineBreakMeasurer.getPosition() < string.length()) {
            lines.add(lineBreakMeasurer.nextLayout(width));
        }

        return lines;
    }
}