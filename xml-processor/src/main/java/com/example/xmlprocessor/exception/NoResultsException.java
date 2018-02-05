package com.example.xmlprocessor.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoResultsException extends Exception {

    public NoResultsException(String s) {
        super(s);
    }
}
