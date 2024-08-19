package com.pulsar.habitica.mapper;

import com.pulsar.habitica.dto.UserDto;
import com.pulsar.habitica.entity.user.User;

public class UserMapper implements Mapper<User, UserDto> {

    private static UserMapper INSTANCE;

    private UserMapper() {}

    @Override
    public UserDto mapFrom(User user) {
        return UserDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }

    public static UserMapper getInstance() {
        if (INSTANCE == null) {
            synchronized (UserMapper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserMapper();
                }
            }
        }
        return INSTANCE;
    }
}
