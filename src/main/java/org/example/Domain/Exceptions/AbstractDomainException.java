package org.example.Domain.Exceptions;

import org.example.Application.Exceptions.AbstractApplicationException;

public abstract class AbstractDomainException extends AbstractApplicationException {
    public AbstractDomainException(String message) {
        super(message);
    }
}
