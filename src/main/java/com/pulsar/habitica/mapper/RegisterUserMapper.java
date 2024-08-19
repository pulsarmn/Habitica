package com.pulsar.habitica.mapper;

import com.pulsar.habitica.dto.RegisterUserDto;
import com.pulsar.habitica.entity.user.User;

public class RegisterUserMapper implements Mapper<RegisterUserDto, User> {

    @Override
    public User mapFrom(RegisterUserDto object) {
        return null;
    }
}
