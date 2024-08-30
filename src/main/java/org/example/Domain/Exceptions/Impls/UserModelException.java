package org.example.Domain.Exceptions.Impls;

import org.example.Domain.Exceptions.AbstractDomainException;

public class UserModelException extends AbstractDomainException {
    public UserModelException(String message) {
        super(message);
    }
}
