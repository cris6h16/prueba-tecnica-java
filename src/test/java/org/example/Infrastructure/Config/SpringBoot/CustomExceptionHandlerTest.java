package org.example.Infrastructure.Config.SpringBoot;

import org.example.Infrastructure.Exceptions.REST.MyResponseStatusException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomExceptionHandlerTest {

    private final CustomExceptionHandler exceptionHandler = new CustomExceptionHandler();

    @Test
    void testHandleMyResponseStatusException() {
        // Arrange
        MyResponseStatusException ex = new MyResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        // Act
        ResponseEntity<CustomExceptionHandler.ErrorResponse> re = exceptionHandler.handleResponseStatusException(ex);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, re.getStatusCode());
        assertEquals("404", re.getBody().status());
        assertEquals("User not found", re.getBody().message());
    }

    @Test
    void testHandleException() {
        // Arrange
        Exception genericException = new Exception("Error inesperado");

        // Act
        ResponseEntity<CustomExceptionHandler.ErrorResponse> re = exceptionHandler.handleException(genericException);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode());
        assertEquals("500", re.getBody().status());
        assertEquals("Error inesperado", re.getBody().message());
    }
}
