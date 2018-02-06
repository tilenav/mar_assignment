package com.example.xmlprocessor.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class WhileSavingException extends Exception {

    // Not in use, but could be used if we had Create methods.
    public WhileSavingException(String s) {
        super(s);
    }
}
