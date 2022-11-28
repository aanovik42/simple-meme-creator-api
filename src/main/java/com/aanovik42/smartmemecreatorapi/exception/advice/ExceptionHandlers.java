package com.aanovik42.smartmemecreatorapi.exception.advice;

import com.aanovik42.smartmemecreatorapi.exception.MemeTemplateIdUnprocessableException;
import com.aanovik42.smartmemecreatorapi.exception.MemeTemplateIdNotFoundException;
import com.aanovik42.smartmemecreatorapi.exception.TextLineTooLongException;
import com.aanovik42.smartmemecreatorapi.exception.TextSizeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.Date;

@ControllerAdvice
public class ExceptionHandlers {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage defaultExceptionHandler(Exception exception) {
        System.out.println(exception.getMessage());
        System.out.println(Arrays.toString(exception.getStackTrace()));
        return new ErrorMessage(new Date(), "Internal Server Error");
    }

    @ResponseBody
    @ExceptionHandler(MemeTemplateIdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage memeTemplateNotFoundHandler(MemeTemplateIdNotFoundException exception) {
        return new ErrorMessage(new Date(), exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = {MemeTemplateIdUnprocessableException.class,
            TextLineTooLongException.class,
            TextSizeException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage badRequestHandler(RuntimeException exception) {
        System.out.println(exception.getMessage());
        return new ErrorMessage(new Date(), exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage methodArgumentNotValidHandler(MethodArgumentNotValidException exception) {

        String message = exception.getBindingResult().getFieldError().getDefaultMessage();
        if (message == null)
            message = "Wrong value in request body";

        return new ErrorMessage(new Date(), message);
    }

}
