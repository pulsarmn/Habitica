package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.user.UserBalanceDaoImpl;
import com.pulsar.habitica.dao.user.UserDao;
import com.pulsar.habitica.dao.user.UserImageDaoImpl;
import com.pulsar.habitica.dao.user.UserStatisticsDaoImpl;
import com.pulsar.habitica.dto.LoginUserDto;
import com.pulsar.habitica.dto.ProfileUserDto;
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

import java.io.IOException;

public class UserService {

    private final UserDao userDao;
    private final Validator<RegisterUserDto> registerValidator;
    private final Validator<LoginUserDto> loginValidator;
    private final Mapper<RegisterUserDto, User> registerUserMapper = RegisterUserMapper.getInstance();
    private final Mapper<User, UserDto> userMapper = UserMapper.getInstance();
    private final UserBalanceService userBalanceService;
    private final UserImageService userImageService;
    private final UserStatisticsService userStatisticsService;

    {
        this.userBalanceService = new UserBalanceService(UserBalanceDaoImpl.getInstance());
        this.userImageService = new UserImageService(UserImageDaoImpl.getInstance());
        this.userStatisticsService = new UserStatisticsService(UserStatisticsDaoImpl.getInstance());
    }

    public UserService(UserDao userDao) {
        this.userDao = userDao;
        this.registerValidator = new RegisterValidator(userDao);
        this.loginValidator = new LoginValidator(userDao);
    }

    public ProfileUserDto create(RegisterUserDto registerUserDto) throws IOException {
        var validationResult = registerValidator.isValid(registerUserDto);
        if (validationResult.isInvalid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var user = registerUserMapper.mapFrom(registerUserDto);

        user = userDao.save(user);

        var userBalance = userBalanceService.initUserBalance(user.getId());
        var userImage = userImageService.initUserImage(user.getId());
        var userStatistics = userStatisticsService.initUserStatistics(user.getId());

        return ProfileUserDto.builder()
                .userDto(userMapper.mapFrom(user))
                .userBalance(userBalance)
                .userImage(userImage)
                .userStatistics(userStatistics)
                .build();
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