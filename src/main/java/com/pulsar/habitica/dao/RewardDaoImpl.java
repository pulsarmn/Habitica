package com.pulsar.habitica.dao;

import com.pulsar.habitica.entity.Reward;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RewardDaoImpl implements RewardDao {

    private static final RewardDaoImpl INSTANCE = new RewardDaoImpl();

    private RewardDaoImpl() {}

    @Override
    public List<Reward> findAll() {
        return null;
    }

    @Override
    public Optional<Reward> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Reward save(Reward entity) {
        return null;
    }

    @Override
    public Reward update(Reward entity) {
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public boolean delete(Reward entity) {
        return false;
    }

    @Override
    public List<Reward> findByHeading(String heading) {
        return null;
    }

    @Override
    public List<Reward> findAllByUserId(Integer userId) {
        return null;
    }

    public static RewardDaoImpl getInstance() {
        return INSTANCE;
    }
}
