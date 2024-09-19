package com.pulsar.habitica.mapper;

import com.pulsar.habitica.dto.RewardDto;
import com.pulsar.habitica.entity.Reward;

public class RewardDtoMapper implements Mapper<RewardDto, Reward> {

    private static RewardDtoMapper INSTANCE;

    @Override
    public Reward mapFrom(RewardDto object) {
        return null;
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
