package com.aggregator.aggregator_website.services.exceptionclient;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ErrorResponseStatus extends ResponseStatusException {

    public ErrorResponseStatus(HttpStatus status, String reason) {
        super(status, reason);
    }
}
