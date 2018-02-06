package com.example.xmlprocessor.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntityExistsException extends Exception {

    // Currently not used, but could be if we had Create methods
    public EntityExistsException(String s) {
        super(s);
    }
}
