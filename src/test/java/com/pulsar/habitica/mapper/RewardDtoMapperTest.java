package com.pulsar.habitica.mapper;

import com.pulsar.habitica.annotation.MapperTest;
import com.pulsar.habitica.annotation.SingletonTest;
import com.pulsar.habitica.dto.RewardDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class RewardDtoMapperTest {

    private static final RewardDto REWARD_DTO = RewardDto.builder()
            .userId(12)
            .heading("REWARD DTO")
            .build();

    @MapperTest
    void successfulMapFromRewardDtoToReward() {
        var mapper = RewardDtoMapper.getInstance();
        var reward = mapper.mapFrom(REWARD_DTO);

        assertThat(reward).isNotNull();
        assertThat(reward.getUserId()).isEqualTo(REWARD_DTO.getUserId());
        assertThat(reward.getHeading()).isEqualTo(REWARD_DTO.getHeading());
        assertThat(reward.getCost()).isEqualTo(new BigDecimal(10));
    }

    @SingletonTest
    void testSingletonInstance() {
        var instance1 = RewardDtoMapper.getInstance();
        var instance2 = RewardDtoMapper.getInstance();

        assertThat(instance1).isEqualTo(instance2);
    }
}
