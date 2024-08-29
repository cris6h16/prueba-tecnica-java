package org.example.Domain.Exceptions;

import org.example.Application.Exceptions.AbstractApplicationException;

public abstract class DomainException extends AbstractApplicationException {
    public DomainException(String message) {
        super(message);
    }
}
