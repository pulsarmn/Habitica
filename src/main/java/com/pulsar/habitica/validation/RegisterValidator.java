package com.pulsar.habitica.validation;

import com.pulsar.habitica.dto.RegisterUserDto;

public class RegisterValidator implements Validator<RegisterUserDto> {

    @Override
    public ValidationResult isValid(RegisterUserDto object) {
        return null;
    }
}
