package com.pulsar.habitica.validation;

import com.pulsar.habitica.dao.user.UserDao;
import com.pulsar.habitica.dto.LoginUserDto;
import lombok.extern.java.Log;

import java.util.stream.Stream;

public class LoginValidator implements Validator<LoginUserDto> {

    private ValidationResult validationResult;
    private final UserDao userDao;
    private static final String EMAIL_PATTERN = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public LoginValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public ValidationResult isValid(LoginUserDto loginUserDto) {
        validationResult = new ValidationResult();
        checkDtoParameters(loginUserDto);
        return validationResult;
    }

    private void checkDtoParameters(LoginUserDto userDto) {
        checkIdentifier(userDto);
    }

    private void checkIdentifier(LoginUserDto userDto) {
        if (userDto.getIdetifier() == null || userDto.getIdetifier().isEmpty()) {
            validationResult.getErrors().add(Error.of("empty", "Input nickname or password!"));
        }else if (userDto.isEmail()) {
            checkEmail(userDto.getIdetifier());
        }else {
            checkNickname(userDto.getIdetifier());
        }
    }

    private void checkEmail(String email) {
        if (!email.matches(EMAIL_PATTERN)) {
            validationResult.getErrors().add(Error.of("invalid", "Invalid email!"));
        }else if (userDao.findByEmail(email).isEmpty()) {
            validationResult.getErrors().add(Error.of("exists", "The user with this email does not exist!"));
        }
    }

    private void checkNickname(String nickname) {
        if (userDao.findByNickname(nickname).isEmpty()) {
            validationResult.getErrors().add(Error.of("exists", "The user with this nickname does not exist"));
        }
    }
}
