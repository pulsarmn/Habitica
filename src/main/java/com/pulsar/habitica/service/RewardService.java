package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.reward.RewardDao;
import com.pulsar.habitica.entity.Reward;

public class RewardService {

    private final RewardDao rewardDao;

    public RewardService(RewardDao rewardDao) {
        this.rewardDao = rewardDao;
    }

    public Reward findById(int rewardId) {
        return rewardDao.findById(rewardId)
                .orElse(Reward.builder().build());
    }
}
