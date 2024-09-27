package com.pulsar.habitica.validation;

import com.pulsar.habitica.dao.user.UserDao;
import com.pulsar.habitica.dto.LoginUserDto;
import com.pulsar.habitica.entity.user.User;

import static com.pulsar.habitica.validation.ErrorCodes.*;

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
        if (validationResult.isValid()) {
            checkPassword(userDto);
        }
    }

    private void checkIdentifier(LoginUserDto userDto) {
        if (userDto.getIdetifier() == null || userDto.getIdetifier().isEmpty()) {
            validationResult.getErrors().add(Error.of(IDENTIFIER_REQUIRED, "Input nickname or email!"));
        }else if (userDto.isEmail()) {
            checkEmail(userDto.getIdetifier());
        }else {
            checkNickname(userDto.getIdetifier());
        }
    }

    private void checkEmail(String email) {
        if (!email.matches(EMAIL_PATTERN)) {
            validationResult.getErrors().add(Error.of(INVALID_EMAIL, "Invalid email!"));
        }else if (userDao.findByEmail(email).isEmpty()) {
            validationResult.getErrors().add(Error.of(EMAIL_NOT_EXIST, "The user with this email does not exist!"));
        }
    }

    private void checkNickname(String nickname) {
        if (userDao.findByNickname(nickname).isEmpty()) {
            validationResult.getErrors().add(Error.of(INVALID_NICKNAME, "The user with this nickname does not exist"));
        }
    }

    private void checkPassword(LoginUserDto userDto) {
        User user = getUserByIdentifier(userDto);

        if (!user.getPassword().equals(userDto.getPassword())) {
            validationResult.getErrors().add(Error.of(INVALID_PASSWORD, "Invalid password!"));
        }
    }

    private User getUserByIdentifier(LoginUserDto userDto) {
        User user;
        if (userDto.isEmail()) {
            user = userDao.findByEmail(userDto.getIdetifier()).get();
        }else {
            user = userDao.findByNickname(userDto.getIdetifier()).get();
        }
        return user;
    }
}