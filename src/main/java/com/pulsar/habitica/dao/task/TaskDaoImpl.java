package com.pulsar.habitica.dao.task;


import com.pulsar.habitica.entity.task.Complexity;
import com.pulsar.habitica.entity.task.Task;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pulsar.habitica.dao.table.TaskTable.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class TaskDaoImpl implements TaskDao<Task> {

    private static final String FIND_ALL_SQL = "SELECT * FROM %s"
            .formatted(FULL_TABLE_NAME);
    private static final String FIND_BY_ID_SQL = "SELECT * FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, ID_COLUMN);
    private static final String SAVE_SQL = "INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)"
            .formatted(FULL_TABLE_NAME,
                    HEADING_COLUMN,
                    DESCRIPTION_COLUMN,
                    COMPLEXITY_COLUMN,
                    DEADLINE_COLUMN,
                    STATUS_COLUMN,
                    USER_ID_COLUMN);
    private static final String UPDATE_SQL = "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?"
            .formatted(FULL_TABLE_NAME,
                    HEADING_COLUMN,
                    DESCRIPTION_COLUMN,
                    COMPLEXITY_COLUMN,
                    DEADLINE_COLUMN,
                    STATUS_COLUMN,
                    ID_COLUMN);
    private static final String DELETE_BY_ID_SQL = "DELETE FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, ID_COLUMN);
    private static final String FIND_BY_HEADING_SQL = "SELECT * FROM %s WHERE LOWER(%s) LIKE CONCAT('%', ?, '%')"
            .replaceFirst("%s", FULL_TABLE_NAME).replaceFirst("%s", HEADING_COLUMN);
    private static final String FIND_ALL_BY_USER_ID_SQL = "SELECT * FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, USER_ID_COLUMN);
    private static TaskDaoImpl INSTANCE;

    private TaskDaoImpl() {}

    @Override
    public List<Task> findAll() {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            return getTaskList(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Task> findById(Integer id) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            var resultSet = statement.executeQuery();
            Task task = null;
            if (resultSet.next()) {
                task = buildTask(resultSet);
            }
            return Optional.ofNullable(task);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Task save(Task entity) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            setTaskParameters(statement, entity);
            statement.setInt(6, entity.getUserId());
            statement.executeUpdate();

            var keys = statement.getGeneratedKeys();
            keys.next();
            entity.setId(keys.getInt(ID_COLUMN));
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Task update(Task entity) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(UPDATE_SQL)) {
            setTaskParameters(statement, entity);
            statement.setInt(6, entity.getId());
            statement.executeUpdate();

            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            statement.setInt(1, id);
            int status = statement.executeUpdate();
            return status == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Task entity) {
        return deleteById(entity.getId());
    }

    @Override
    public List<Task> findByHeading(String heading) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_BY_HEADING_SQL)) {
            statement.setString(1, heading.toLowerCase());

            return getTaskList(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Task> findAllByUserId(Integer userId) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_ALL_BY_USER_ID_SQL)) {
            statement.setInt(1, userId);

            return getTaskList(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Task> getTaskList(PreparedStatement statement) throws SQLException {
        var resultSet = statement.executeQuery();
        List<Task> tasks = new ArrayList<>();
        while (resultSet.next()) {
            Task task = buildTask(resultSet);
            tasks.add(task);
        }
        return tasks;
    }

    private Task buildTask(ResultSet resultSet) throws SQLException {
        return Task.builder()
                .id(resultSet.getInt(ID_COLUMN))
                .heading(resultSet.getString(HEADING_COLUMN))
                .description(resultSet.getString(DESCRIPTION_COLUMN))
                .complexity(Complexity.valueOf(resultSet.getString(COMPLEXITY_COLUMN) != null
                        ? resultSet.getString(COMPLEXITY_COLUMN)
                        : Complexity.EASY.name()))
                .deadline(resultSet.getDate(DEADLINE_COLUMN) != null
                        ? resultSet.getDate(DEADLINE_COLUMN).toLocalDate()
                        : null)
                .status(resultSet.getBoolean(STATUS_COLUMN))
                .userId(resultSet.getInt(USER_ID_COLUMN))
                .build();
    }

    private void setTaskParameters(PreparedStatement statement, Task entity) throws SQLException {
        statement.setString(1, entity.getHeading());
        statement.setString(2, entity.getDescription());
        statement.setString(3, entity.getComplexity() != null ? entity.getComplexity().name() : Complexity.EASY.name());
        statement.setDate(4, entity.getDeadline() != null ? Date.valueOf(entity.getDeadline()) : null);
        statement.setBoolean(5, entity.getStatus());
    }

    public static TaskDaoImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (TaskDaoImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TaskDaoImpl();
                }
            }
        }
        return INSTANCE;
    }
}