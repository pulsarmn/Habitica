package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.user.UserDao;
import com.pulsar.habitica.dto.LoginUserDto;
import com.pulsar.habitica.dto.RegisterUserDto;
import com.pulsar.habitica.dto.UserDto;
import com.pulsar.habitica.entity.user.User;
import com.pulsar.habitica.exception.ValidationException;
import com.pulsar.habitica.mapper.Mapper;
import com.pulsar.habitica.mapper.RegisterUserMapper;
import com.pulsar.habitica.mapper.UserMapper;
import com.pulsar.habitica.validation.LoginValidator;
import com.pulsar.habitica.validation.RegisterValidator;
import com.pulsar.habitica.validation.Validator;

public class UserService {

    private final UserDao userDao;
    private final Validator<RegisterUserDto> registerValidator;
    private final Validator<LoginUserDto> loginValidator;
    private final Mapper<RegisterUserDto, User> registerUserMapper = RegisterUserMapper.getInstance();
    private final Mapper<User, UserDto> userMapper = UserMapper.getInstance();

    public UserService(UserDao userDao) {
        this.userDao = userDao;
        this.registerValidator = new RegisterValidator(userDao);
        this.loginValidator = new LoginValidator(userDao);
    }

    public UserDto create(RegisterUserDto registerUserDto) {
        var validationResult = registerValidator.isValid(registerUserDto);
        if (validationResult.isInvalid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        User user = registerUserMapper.mapFrom(registerUserDto);
        user = userDao.save(user);
        return userMapper.mapFrom(user);
    }

    public UserDto login(LoginUserDto loginUserDto) {
        var validationResult = loginValidator.isValid(loginUserDto);
        if (validationResult.isInvalid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        User user;
        if (loginUserDto.isEmail()) {
            user = userDao.findByEmail(loginUserDto.getIdetifier()).get();
        }else {
            user = userDao.findByNickname(loginUserDto.getIdetifier()).get();
        }
        return userMapper.mapFrom(user);
     }
}