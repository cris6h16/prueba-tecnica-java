package org.example.Application.Exceptions.Impls;

public class NullCommandException extends RuntimeException {
    public NullCommandException() {
        super("Command cannot be null");
    }
}
