package org.example.Domain.Exceptions.Impls;

import org.example.Domain.Exceptions.DomainException;

public class PostModelException extends DomainException {
    public PostModelException(String message) {
        super(message);
    }
}
