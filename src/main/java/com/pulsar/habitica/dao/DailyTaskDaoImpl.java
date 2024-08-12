package com.pulsar.habitica.dao;

import com.pulsar.habitica.entity.DailyTask;
import com.pulsar.habitica.entity.Task;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DailyTaskDaoImpl implements TaskDao<DailyTask> {

    private static final String FIND_ALL_SQL = "SELECT * FROM task.daily_task";

    @Override
    public List<DailyTask> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = statement.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<DailyTask> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public DailyTask save(DailyTask entity) {
        return null;
    }

    @Override
    public DailyTask update(DailyTask entity) {
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public boolean delete(DailyTask entity) {
        return false;
    }

    @Override
    public List<DailyTask> findByHeading(String heading) {
        return null;
    }

    @Override
    public List<DailyTask> findAllByUserId(Integer userId) {
        return null;
    }
}
