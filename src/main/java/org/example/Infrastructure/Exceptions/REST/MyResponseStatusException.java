package org.example.Infrastructure.Exceptions.REST;

import org.example.Infrastructure.Exceptions.InfrastructureException;
import org.springframework.http.HttpStatus;

public class MyResponseStatusException extends InfrastructureException {
    private final HttpStatus status;
    private final String response;

    public MyResponseStatusException(HttpStatus status, String response) {
        super(response);
        this.status = status;
        this.response = response;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }
}
