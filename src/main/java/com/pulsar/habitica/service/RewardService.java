package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.reward.RewardDao;
import com.pulsar.habitica.entity.Reward;

import java.util.List;

public class RewardService {

    private final RewardDao rewardDao;

    public RewardService(RewardDao rewardDao) {
        this.rewardDao = rewardDao;
    }

    public Reward findById(int rewardId) {
        return rewardDao.findById(rewardId)
                .orElse(Reward.builder().build());
    }

    public List<Reward> findAllByUserId(int userId) {
        return rewardDao.findAllByUserId(userId);
    }

    public Reward update(Reward reward) {
        return rewardDao.update(reward);
    }
}
