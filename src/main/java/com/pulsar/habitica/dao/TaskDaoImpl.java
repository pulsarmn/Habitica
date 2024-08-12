package com.pulsar.habitica.dao;


import com.pulsar.habitica.entity.Complexity;
import com.pulsar.habitica.entity.Task;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskDaoImpl implements TaskDao {

    private static final String FIND_ALL_SQL = "SELECT * FROM task.task";
    private static final TaskDaoImpl INSTANCE = new TaskDaoImpl();

    private TaskDaoImpl() {}

    @Override
    public List<Task> findAll() {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = statement.executeQuery();
            List<Task> tasks = new ArrayList<>();
            while (resultSet.next()) {
                Task task = buildTask(resultSet);
                tasks.add(task);
            }
            return tasks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    private Task buildTask(ResultSet resultSet) throws SQLException {
        return Task.builder()
                .id(resultSet.getInt("id"))
                .heading(resultSet.getString("heading"))
                .description(resultSet.getString("description"))
                .complexity(Complexity.valueOf(resultSet.getString("complexity")))
                .deadline(resultSet.getDate("deadline").toLocalDate())
                .status(resultSet.getBoolean("status"))
                .build();
    }

    public static TaskDaoImpl getInstance() {
        return INSTANCE;
    }
}