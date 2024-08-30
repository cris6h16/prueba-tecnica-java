package org.example.Infrastructure.Exceptions.REST;

import org.example.Infrastructure.Exceptions.AbstractInfrastructureException;
import org.springframework.http.HttpStatus;

public class MyResponseStatusException extends AbstractInfrastructureException {
    private final HttpStatus status;

    public MyResponseStatusException(HttpStatus status, String response) {
        super(response);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
