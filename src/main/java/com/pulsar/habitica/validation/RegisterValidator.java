package com.pulsar.habitica.validation;

import com.pulsar.habitica.dao.user.UserDao;
import com.pulsar.habitica.dto.RegisterUserDto;

public class RegisterValidator implements Validator<RegisterUserDto> {

    private final UserDao userDao;

    public RegisterValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public ValidationResult isValid(RegisterUserDto object) {
        return null;
    }
}
