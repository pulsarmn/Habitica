package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.reward.RewardDao;
import com.pulsar.habitica.dto.RewardDto;
import com.pulsar.habitica.entity.Reward;
import com.pulsar.habitica.exception.UnauthorizedException;
import com.pulsar.habitica.mapper.Mapper;
import com.pulsar.habitica.mapper.RewardDtoMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
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

    public Reward updateReward(int userId, JSONObject rewardData) {
        if (rewardData == null || rewardData.isEmpty()) {
            throw new JSONException("JSON is invalid or empty");
        }

        int rewardId = rewardData.getInt("id");
        Reward reward = findById(rewardId);

        if (reward.getUserId() != userId) {
            throw new UnauthorizedException();
        }

        int cost = rewardData.getInt("cost");
        if (cost < 0) {
            throw new IllegalArgumentException("Illegal argument cost: " + cost);
        }

        reward.setHeading(rewardData.getString("heading"));
        reward.setDescription(rewardData.getString("description"));
        reward.setCost(new BigDecimal(cost));

        return rewardDao.update(reward);
    }

    public boolean deleteReward(int rewardId) {
        return rewardDao.deleteById(rewardId);
    }
}
