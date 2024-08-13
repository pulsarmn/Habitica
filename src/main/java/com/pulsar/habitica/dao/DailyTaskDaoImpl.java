package com.pulsar.habitica.dao;

import com.pulsar.habitica.entity.Complexity;
import com.pulsar.habitica.entity.DailyTask;
import com.pulsar.habitica.entity.Task;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DailyTaskDaoImpl implements TaskDao<DailyTask> {

    private static final String FIND_ALL_SQL = "SELECT * FROM task.daily_task";
    private static final DailyTaskDaoImpl INSTANCE = new DailyTaskDaoImpl();

    private DailyTaskDaoImpl() {}

    @Override
    public List<DailyTask> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = statement.executeQuery();
            List<DailyTask> tasks = new ArrayList<>();
            while (resultSet.next()) {
                DailyTask task = buildDailyTask(resultSet);
                tasks.add(task);
            }
            return tasks;
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

    public DailyTask buildDailyTask(ResultSet resultSet) throws SQLException {
        return DailyTask.builder()
                .id(resultSet.getInt("id"))
                .heading(resultSet.getString("heading"))
                .description(resultSet.getString("description"))
                .complexity(Complexity.valueOf(resultSet.getString("complexity")))
                .deadline(resultSet.getDate("deadline").toLocalDate())
                .status(resultSet.getBoolean("status"))
                .series(resultSet.getInt("series"))
                .userId(resultSet.getInt("user_id"))
                .build();
    }

    public static DailyTaskDaoImpl getInstance() {
        return INSTANCE;
    }
}
