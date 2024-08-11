package com.pulsar.habitica.dao;

import com.pulsar.habitica.dao.TaskDao;
import com.pulsar.habitica.entity.Task;

import java.util.List;
import java.util.Optional;

public class TaskDaoImpl implements TaskDao {

    @Override
    public List<Task> findAll() {
        return null;
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Task save(Task entity) {
        return null;
    }

    @Override
    public Task update(Task entity) {
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public boolean delete(Task entity) {
        return false;
    }

    @Override
    public List<Task> findByHeading(String heading) {
        return null;
    }
}
