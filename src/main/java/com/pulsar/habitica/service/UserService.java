package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.user.UserDao;
import com.pulsar.habitica.dto.RegisterUserDto;
import com.pulsar.habitica.entity.user.User;

public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User create(RegisterUserDto userDto) {
        return null;
    }
}