package com.pulsar.habitica.dao.reward;

import com.pulsar.habitica.dao.Dao;
import com.pulsar.habitica.entity.Reward;

import java.util.List;

public interface RewardDao extends Dao<Integer, Reward> {

    List<Reward> findByHeading(String heading);

    List<Reward> findAllByUserId(Integer userId);
}
