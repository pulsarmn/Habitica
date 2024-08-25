package com.pulsar.habitica.validation;

import com.pulsar.habitica.dao.user.UserDao;
import com.pulsar.habitica.dto.RegisterUserDto;

import static com.pulsar.habitica.validation.ErrorCodes.*;

public class RegisterValidator implements Validator<RegisterUserDto> {

    private ValidationResult validationResult;
    private final UserDao userDao;
    private static final byte MAX_NICKNAME_LENGTH = 30;
    private static final String EMAIL_PATTERN = "\\w+@\\w+.\\w+";
    private static final byte MIN_PASSWORD_LENGTH = 8;
    private static final byte MAX_PASSWORD_LENGTH = 60;

    public RegisterValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public ValidationResult isValid(RegisterUserDto userDto) {
        validationResult = new ValidationResult();
        checkDtoParameters(userDto);
        return validationResult;
    }

    private void checkDtoParameters(RegisterUserDto userDto) {
        checkNickname(userDto.getNickname().trim());
        checkEmail(userDto.getEmail().trim());
        checkPassword(userDto.getPassword());
        checkDoublePassword(userDto);
    }

    private void checkNickname(String nickname) {
        if (nickname == null || nickname.isEmpty()) {
            validationResult.getErrors().add(Error.of(NICKNAME_REQUIRED, "Input nickname!"));
        }else if (userDao.findByNickname(nickname).isPresent()) {
            validationResult.getErrors().add(Error.of(NICKNAME_TAKEN, "This nickname already exists!"));
        }else if (nickname.length() > MAX_NICKNAME_LENGTH) {
            validationResult.getErrors().add(Error.of(LONG_NICKNAME, "Nickname is too long!"));
        }else if (nickname.matches("\\d+")) {
            validationResult.getErrors().add(Error.of(INVALID_NICKNAME, "Nickname must contains characters!"));
        }
    }

    private void checkEmail(String email) {
        if (email == null || email.isEmpty()) {
            validationResult.getErrors().add(Error.of(EMAIL_REQUIRED, "Input email!"));
        }else if (!email.matches(EMAIL_PATTERN)) {
            validationResult.getErrors().add(Error.of(INVALID_EMAIL, "Invalid email!"));
        }else if (userDao.findByEmail(email).isPresent()) {
            validationResult.getErrors().add(Error.of(EMAIL_TAKEN, "Account with this email already exists!"));
        }
    }

    private void checkPassword(String password) {
        if (password == null || password.isEmpty()) {
            validationResult.getErrors().add(Error.of(PASSWORD_REQUIRED, "Input password!"));
        }else if (password.length() < MIN_PASSWORD_LENGTH) {
            validationResult.getErrors().add(Error.of(SHORT_PASSWORD, "Password is too short!"));
        }else if (password.length() > MAX_PASSWORD_LENGTH) {
            validationResult.getErrors().add(Error.of(LONG_PASSWORD, "Password is too long!"));
        }
    }

    private void checkDoublePassword(RegisterUserDto userDto) {
        if (!userDto.getPassword().equals(userDto.getDoublePassword())) {
            validationResult.getErrors().add(Error.of(INVALID_PASSWORD, "Password aren`t equals!"));
        }
    }
}