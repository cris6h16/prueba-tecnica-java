package org.example.Application.Exceptions;

public abstract class AbstractApplicationException extends RuntimeException {
    public AbstractApplicationException(String message) {
        super(message);
    }
}
