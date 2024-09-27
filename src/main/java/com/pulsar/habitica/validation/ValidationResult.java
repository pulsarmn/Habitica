package com.pulsar.habitica.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private final List<Error> errors;

    public ValidationResult() {
        errors = new ArrayList<>();
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public boolean isInvalid() {
        return !isValid();
    }

    public List<Error> getErrors() {
        return errors;
    }
}
