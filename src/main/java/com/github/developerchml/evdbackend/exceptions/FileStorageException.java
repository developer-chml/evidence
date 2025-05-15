package com.github.developerchml.evdbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileStorageException extends CustomException {
    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
