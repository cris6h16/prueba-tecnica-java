package org.example.Domain.Exceptions.Impls;

import org.example.Domain.Exceptions.DomainException;

public class UserModelException extends DomainException {
    public UserModelException(String message) {
        super(message);
    }
}
