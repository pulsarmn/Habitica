package com.pulsar.habitica.validation;

public interface Validator<T> {

    ValidationResult isValid(T object);
}
