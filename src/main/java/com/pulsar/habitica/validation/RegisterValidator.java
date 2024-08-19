package com.pulsar.habitica.validation;

import com.pulsar.habitica.dao.user.UserDao;
import com.pulsar.habitica.dto.RegisterUserDto;

public class RegisterValidator implements Validator<RegisterUserDto> {

    private final ValidationResult validationResult;
    private final UserDao userDao;

    public RegisterValidator(UserDao userDao) {
        validationResult = new ValidationResult();
        this.userDao = userDao;
    }

    @Override
    public ValidationResult isValid(RegisterUserDto object) {
        if (userDao.findByNickname(object.getNickname()).isEmpty()) {
            
        }
    }
}
