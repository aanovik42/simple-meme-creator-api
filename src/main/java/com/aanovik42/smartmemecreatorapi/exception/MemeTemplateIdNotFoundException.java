package com.aanovik42.smartmemecreatorapi.exception;

public class MemeTemplateIdNotFoundException extends MemeTemplateIdException {

    public MemeTemplateIdNotFoundException(Long id) {
        super(id);
    }
}
