package org.example.Application.Exceptions.Impls;

import org.example.Application.Exceptions.AbstractApplicationException;

public class UserNotFoundException extends AbstractApplicationException {
    public UserNotFoundException(String username) {
        super("No se encontró ningún usuario @" + username);
    }
}
