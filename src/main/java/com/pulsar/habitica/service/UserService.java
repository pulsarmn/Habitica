package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.user.UserDao;
import com.pulsar.habitica.dto.RegisterUserDto;
import com.pulsar.habitica.dto.UserDto;
import com.pulsar.habitica.entity.user.User;
import com.pulsar.habitica.exception.ValidationException;
import com.pulsar.habitica.mapper.Mapper;
import com.pulsar.habitica.mapper.RegisterUserMapper;
import com.pulsar.habitica.mapper.UserMapper;
import com.pulsar.habitica.validation.RegisterValidator;
import com.pulsar.habitica.validation.Validator;

public class UserService {

    private final UserDao userDao;
    private final Validator<RegisterUserDto> validator;
    private final Mapper<RegisterUserDto, User> registerUserMapper = RegisterUserMapper.getInstance();
    private final Mapper<User, UserDto> userMapper = UserMapper.getInstance();

    public UserService(UserDao userDao) {
        this.userDao = userDao;
        this.validator = new RegisterValidator(userDao);
    }

    public UserDto create(RegisterUserDto registerUserDto) {
        var validationResult = validator.isValid(registerUserDto);
        if (validationResult.isInvalid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        User user = registerUserMapper.mapFrom(registerUserDto);
        user = userDao.save(user);
        return userMapper.mapFrom(user);
    }
}