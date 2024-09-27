package com.pulsar.habitica.exception;

import com.pulsar.habitica.validation.Error;

import java.util.List;

public class ValidationException extends RuntimeException {

    private final List<Error> errors;

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }

    public List<Error> getErrors() {
        return errors;
    }
}
