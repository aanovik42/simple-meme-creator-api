package com.aanovik42.smartmemecreatorapi.exception;

public class TextSizeException extends RuntimeException {

    int boxCount;

    public TextSizeException(int boxCount) {
        this.boxCount = boxCount;
    }

    @Override
    public String getMessage() {
        String message = "A text should be " + boxCount + " lines";
        return message;
    }

}
