package com.pulsar.habitica.dao;

import com.pulsar.habitica.entity.Habit;
import com.pulsar.habitica.entity.Task;

import java.util.List;
import java.util.Optional;

public class HabitDaoImpl implements TaskDao<Habit> {

    @Override
    public List<Habit> findAll() {
        return null;
    }

    @Override
    public Optional<Habit> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Habit save(Habit entity) {
        return null;
    }

    @Override
    public Habit update(Habit entity) {
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public boolean delete(Habit entity) {
        return false;
    }

    @Override
    public List<Habit> findByHeading(String heading) {
        return null;
    }

    @Override
    public List<Habit> findAllByUserId(Integer userId) {
        return null;
    }
}
