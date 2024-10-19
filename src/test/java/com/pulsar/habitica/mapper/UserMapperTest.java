package com.pulsar.habitica.mapper;

import com.pulsar.habitica.annotation.MapperTest;
import com.pulsar.habitica.entity.user.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {

    private static final User HUMAN_1 = User.builder()
            .id(12)
            .nickname("human_1")
            .email("human_1@yandex.ru")
            .password("1234567899ab")
            .createdAt(LocalDate.of(2024, 10, 19))
            .build();

    @MapperTest
    void successMapFromUserToUserDto() {
        var mapper = UserMapper.getInstance();
        var userDto = mapper.mapFrom(HUMAN_1);

        assertThat(userDto).isNotNull();
        assertThat(userDto.getId()).isEqualTo(HUMAN_1.getId());
        assertThat(userDto.getNickname()).isEqualTo(HUMAN_1.getNickname());
        assertThat(userDto.getEmail()).isEqualTo(HUMAN_1.getEmail());
    }

    @Test
    void testSingletonInstance() {
        var instance1 = UserMapper.getInstance();
        var instance2 = UserMapper.getInstance();

        assertThat(instance1).isEqualTo(instance2);
    }
}
