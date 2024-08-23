package com.pulsar.habitica.validation;

import com.pulsar.habitica.dao.user.UserDao;
import com.pulsar.habitica.dto.LoginUserDto;

public class LoginValidator implements Validator<LoginUserDto> {

    private ValidationResult validationResult;
    private final UserDao userDao;

    public LoginValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public ValidationResult isValid(LoginUserDto object) {
        return null;
    }
}
