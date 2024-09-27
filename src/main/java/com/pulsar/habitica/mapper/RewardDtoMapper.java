package com.pulsar.habitica.mapper;

import com.pulsar.habitica.dto.RewardDto;
import com.pulsar.habitica.entity.Reward;

import java.math.BigDecimal;

public class RewardDtoMapper implements Mapper<RewardDto, Reward> {

    private static RewardDtoMapper INSTANCE;

    @Override
    public Reward mapFrom(RewardDto entity) {
        return Reward.builder()
                .heading(entity.getHeading())
                .cost(new BigDecimal(10))
                .userId(entity.getUserId())
                .build();
    }

    public static RewardDtoMapper getInstance() {
        if (INSTANCE == null) {
            synchronized (RewardDtoMapper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RewardDtoMapper();
                }
            }
        }
        return INSTANCE;
    }
}
