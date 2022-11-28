package com.aanovik42.smartmemecreatorapi.exception;

public class TextLineTooLongException extends RuntimeException {

    private int textId;
    private String textLine;

    public TextLineTooLongException(int textId, String textLine) {
        this.textId = textId;
        this.textLine = textLine;
    }

    @Override
    public String getMessage() {
        String message = "Line is too long: " +
                "'" + textLine + "'";

        return message;
    }


}
