package com.pulsar.habitica.validation;

import com.pulsar.habitica.dao.user.UserDao;
import com.pulsar.habitica.dto.RegisterUserDto;

public class RegisterValidator implements Validator<RegisterUserDto> {

    private final ValidationResult validationResult;
    private final UserDao userDao;
    private static final String EMAIL_PATTERN = "\\w+@\\w+.\\w+";

    public RegisterValidator(UserDao userDao) {
        validationResult = new ValidationResult();
        this.userDao = userDao;
    }

    @Override
    public ValidationResult isValid(RegisterUserDto userDto) {
        checkDtoParameters(userDto);
        return validationResult;
    }

    private void checkDtoParameters(RegisterUserDto userDto) {
        checkNickname(userDto.getNickname().trim());
        checkEmail(userDto.getEmail().trim());
    }

    private void checkNickname(String nickname) {
        if (nickname == null || nickname.isEmpty()) {
            validationResult.getErrors().add(Error.of("empty", "Input nickname!"));
        }else if (userDao.findByNickname(nickname).isPresent()) {
            validationResult.getErrors().add(Error.of("exists", "This nickname already exists!"));
        }else if (nickname.length() > 50) {
            validationResult.getErrors().add(Error.of("too-long", "Nickname is too long!"));
        }else if (nickname.matches("\\d+")) {
            validationResult.getErrors().add(Error.of("invalid", "Nickname must contains characters!"));
        }
    }

    private void checkEmail(String email) {
        if (email == null || email.isEmpty()) {
            validationResult.getErrors().add(Error.of("empty", "Input email!"));
        }else if (!email.matches(EMAIL_PATTERN)) {
            validationResult.getErrors().add(Error.of("invalid", "Invalid email!"));
        }else if (userDao.findByEmail(email).isPresent()) {
            validationResult.getErrors().add(Error.of("exists", "Account with this email already exists!"));
        }
    }
}
