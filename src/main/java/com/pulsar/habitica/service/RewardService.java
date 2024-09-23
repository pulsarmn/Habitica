package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.reward.RewardDao;
import com.pulsar.habitica.dto.RewardDto;
import com.pulsar.habitica.entity.Reward;
import com.pulsar.habitica.mapper.Mapper;
import com.pulsar.habitica.mapper.RewardDtoMapper;

import java.util.List;

public class RewardService {

    private final RewardDao rewardDao;
    private final Mapper<RewardDto, Reward> rewardDtoMapper = RewardDtoMapper.getInstance();

    public RewardService(RewardDao rewardDao) {
        this.rewardDao = rewardDao;
    }

    public Reward createReward(RewardDto rewardDto) {
        var reward = rewardDtoMapper.mapFrom(rewardDto);
        return rewardDao.save(reward);
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

    public boolean deleteReward(int rewardId) {
        return rewardDao.deleteById(rewardId);
    }
}
