package com.pulsar.habitica.dao;

import com.pulsar.habitica.entity.Reward;

import java.util.List;

public interface RewardDao extends Dao<Integer, Reward>{

    List<Reward> findByHeading(String heading);

    List<Reward> findAllByUserId(Integer userId);
}
