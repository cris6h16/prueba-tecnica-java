package org.example.Infrastructure.Exceptions;

public abstract class InfrastructureException extends RuntimeException {
    public InfrastructureException(String message) {
        super(message);
    }

}
