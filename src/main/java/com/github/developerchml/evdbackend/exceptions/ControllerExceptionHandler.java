package com.github.developerchml.evdbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class ControllerExceptionHandler {
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    private ProblemDetail defineProblemDetail(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, e.getMessage());
        problemDetail.setType(URI.create(""));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    private ProblemDetail defineProblemDetailError(FileNotAcceptedException e) {
        ProblemDetail problemDetail = defineProblemDetail(e);
        if(Objects.nonNull(e.getErrors()) && !e.getErrors().isEmpty())
            problemDetail.setProperties(Map.of("errors",e.getErrors()));
        return problemDetail;
    }

    private HttpStatus getResponseStatus(CustomException ex) {
        ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
        return responseStatus == null ? httpStatus : responseStatus.value();
    }

    @ExceptionHandler(FileNotAcceptedException.class)
    public ResponseEntity<ProblemDetail> fileNotAcceptedExceptionHandler(FileNotAcceptedException ex) {
        httpStatus = getResponseStatus(ex);
        return ResponseEntity.status(httpStatus).body(defineProblemDetailError(ex));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ProblemDetail> CustomExceptionHandler(CustomException ex) {
        httpStatus = getResponseStatus(ex);
        return ResponseEntity.status(httpStatus).body(defineProblemDetail(ex));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> ExceptionHandler(Exception ex) {
        return ResponseEntity.internalServerError().body(defineProblemDetail(ex));
    }
}
