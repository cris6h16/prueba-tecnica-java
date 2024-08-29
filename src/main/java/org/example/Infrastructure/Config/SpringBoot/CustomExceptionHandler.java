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
    public ResponseEntity<String> handleResponseStatusException(MyResponseStatusException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getResponse());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("Error inesperado:" + e.toString());
        return ResponseEntity.status(500).body("Ocurrío un error inesperado");
    }
}
