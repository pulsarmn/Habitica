package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.reward.RewardDao;

public class RewardService {

    private final RewardDao rewardDao;

    public RewardService(RewardDao rewardDao) {
        this.rewardDao = rewardDao;
    }
}
