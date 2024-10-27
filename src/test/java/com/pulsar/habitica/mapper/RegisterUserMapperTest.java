package com.pulsar.habitica.mapper;

import com.pulsar.habitica.annotation.MapperTest;
import com.pulsar.habitica.annotation.SingletonTest;
import com.pulsar.habitica.dto.RegisterUserDto;

import static org.assertj.core.api.Assertions.*;

public class RegisterUserMapperTest {

    private static final RegisterUserDto USER_DTO = RegisterUserDto.builder()
            .nickname("USER_NICKNAME")
            .email("USER@yandex.ru")
            .password("o4u23oi5ui2ou")
            .build();

    @MapperTest
    void successfulMapFromRegisterUserDtoToUser() {
        var mapper = RegisterUserMapper.getInstance();
        var user = mapper.mapFrom(USER_DTO);

        assertThat(user).isNotNull();
        assertThat(user.getNickname()).isEqualTo(USER_DTO.getNickname());
        assertThat(user.getEmail()).isEqualTo(USER_DTO.getEmail());
        assertThat(user.getPassword()).isEqualTo(USER_DTO.getPassword());
    }

    @SingletonTest
    void testSingletonInstance() {
        var instance1 = RegisterUserMapper.getInstance();
        var instance2 = RegisterUserMapper.getInstance();

        assertThat(instance1).isEqualTo(instance2);
    }
}
