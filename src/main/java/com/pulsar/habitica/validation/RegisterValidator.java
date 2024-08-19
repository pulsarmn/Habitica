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
    public ValidationResult isValid(RegisterUserDto userDto) {
        checkDtoParameters(userDto);
        return validationResult;
    }

    private void checkDtoParameters(RegisterUserDto userDto) {
        checkNickname(userDto.getNickname());
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
}
