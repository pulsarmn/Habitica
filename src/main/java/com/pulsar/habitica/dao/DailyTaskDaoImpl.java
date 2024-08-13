package com.pulsar.habitica.dao;

import com.pulsar.habitica.entity.Complexity;
import com.pulsar.habitica.entity.DailyTask;
import com.pulsar.habitica.entity.Task;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class DailyTaskDaoImpl implements TaskDao<DailyTask> {

    private static final String FIND_ALL_SQL = "SELECT * FROM task.daily_task";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM task.daily_task WHERE id = ?";
    private static final String SAVE_SQL = """
            INSERT INTO task.daily_task (heading, description, complexity, deadline, status, series, user_id)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE task.daily_task
            SET heading = ?,
            description = ?,
            complexity = ?,
            deadline = ?,
            status = ?,
            series = ?
            """;
    private static final String DELETE_BY_ID_SQL = "DELETE FROM task.daily_task WHERE id = ?";
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
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            var resultSet = statement.executeQuery();
            DailyTask task = null;
            if (resultSet.next()) {
                task = buildDailyTask(resultSet);
            }
            return Optional.ofNullable(task);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DailyTask save(DailyTask entity) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            setDailyTaskParameters(statement, entity);
            statement.setInt(7, entity.getUserId());
            statement.executeUpdate();

            var resultSet = statement.getGeneratedKeys();
            resultSet.next();
            entity.setId(resultSet.getInt("id"));
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DailyTask update(DailyTask entity) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(UPDATE_SQL)) {
            setDailyTaskParameters(statement, entity);
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

    public void setDailyTaskParameters(PreparedStatement statement, DailyTask entity) throws SQLException {
        statement.setString(1, entity.getHeading());
        statement.setString(2, entity.getDescription());
        statement.setString(3, entity.getComplexity().name());
        statement.setDate(4, Date.valueOf(entity.getDeadline()));
        statement.setBoolean(5, entity.getStatus());
        statement.setInt(6, entity.getSeries());
    }

    public static DailyTaskDaoImpl getInstance() {
        return INSTANCE;
    }
}
