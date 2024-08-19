package com.pulsar.habitica.mapper;

import com.pulsar.habitica.dto.RegisterUserDto;
import com.pulsar.habitica.entity.user.User;

public class RegisterUserMapper implements Mapper<RegisterUserDto, User> {

    private static RegisterUserMapper INSTANCE;

    private RegisterUserMapper() {}

    @Override
    public User mapFrom(RegisterUserDto userDto) {
        return User.builder()
                .nickname(userDto.getNickname())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }

    public static RegisterUserMapper getInstance() {
        if (INSTANCE == null) {
            synchronized (RegisterUserMapper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RegisterUserMapper();
                }
            }
        }
        return INSTANCE;
    }
}
