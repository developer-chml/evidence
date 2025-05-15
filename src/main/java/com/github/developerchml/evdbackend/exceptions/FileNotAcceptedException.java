package com.github.developerchml.evdbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileNotAcceptedException extends CustomException {
    private List<String> errors;
    
    public FileNotAcceptedException(String message) {
        super(message);
    }

    public FileNotAcceptedException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
