package com.pairlearning.expensetrackerapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EtResouceNotFoundException extends RuntimeException {
    public EtResouceNotFoundException(String message) {
        super(message);
    }
}
