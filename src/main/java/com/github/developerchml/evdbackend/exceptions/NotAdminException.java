package com.github.developerchml.evdbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class NotAdminException extends CustomException {
    public NotAdminException(String message) {
        super(message);
    }
}
