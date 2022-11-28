package com.aanovik42.smartmemecreatorapi.exception;

public abstract class MemeTemplateIdException extends RuntimeException {

    public MemeTemplateIdException(Long id) {
        super("Could not find a template with id " + id);
    }
}
