package org.example.Infrastructure.Config.SpringBoot;

import org.example.Infrastructure.Exceptions.REST.MyResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(MyResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(MyResponseStatusException e) {
        return ResponseEntity.status(e.getStatus()).body(
                new ErrorResponse(String.valueOf(e.getStatus().value()), e.getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Error inesperado:" + e.toString());
        return ResponseEntity.status(500).body(new ErrorResponse("500", "Error inesperado"));
    }


    public record ErrorResponse(String status, String message) {
    }
}
