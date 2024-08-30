package org.example.Domain.Exceptions.Impls;

import org.example.Domain.Exceptions.AbstractDomainException;

public class PostModelException extends AbstractDomainException {
    public PostModelException(String message) {
        super(message);
    }
}
