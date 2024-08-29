package org.example.Application.Exceptions.Impls;

import org.example.Application.Exceptions.AbstractApplicationException;

public class UsernameIsNullOrBlankException extends AbstractApplicationException{
    public UsernameIsNullOrBlankException() {
        super("username no puede ser null o vacio");
    }
}
