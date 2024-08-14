package com.pulsar.habitica.dao;

import com.pulsar.habitica.entity.Complexity;
import com.pulsar.habitica.entity.DailyTask;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pulsar.habitica.dao.DailyTaskTable.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class DailyTaskDaoImpl implements TaskDao<DailyTask> {

    private static final String FIND_ALL_SQL = "SELECT * FROM %s"
            .formatted(FULL_TABLE_NAME);
    private static final String FIND_BY_ID_SQL = "SELECT * FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, ID_COLUMN);
    private static final String SAVE_SQL = "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?)".
            formatted(FULL_TABLE_NAME,
                    HEADING_COLUMN,
                    DESCRIPTION_COLUMN,
                    COMPLEXITY_COLUMN,
                    DEADLINE_COLUMN,
                    STATUS_COLUMN,
                    SERIES_COLUMN,
                    USER_ID_COLUMN);
    private static final String UPDATE_SQL = "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?"
            .formatted(FULL_TABLE_NAME,
                    HEADING_COLUMN,
                    DESCRIPTION_COLUMN,
                    COMPLEXITY_COLUMN,
                    DEADLINE_COLUMN,
                    STATUS_COLUMN,
                    SERIES_COLUMN,
                    ID_COLUMN);
    private static final String DELETE_BY_ID_SQL = "DELETE FROM %s WHERE %s = ?".
            formatted(FULL_TABLE_NAME, ID_COLUMN);
    private static final String FIND_BY_HEADING_SQL = "SELECT * FROM %s WHERE LOWER(%s) LIKE CONCAT('%', ?, '%')"
            .replaceFirst("%s", FULL_TABLE_NAME).replaceFirst("%s", HEADING_COLUMN);
    private static final String FIND_BY_USER_ID_SQL = "SELECT * FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, USER_ID_COLUMN);
    private static final DailyTaskDaoImpl INSTANCE = new DailyTaskDaoImpl();

    private DailyTaskDaoImpl() {}

    @Override
    public List<DailyTask> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            return getDailyTaskList(statement);
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
            entity.setId(resultSet.getInt(ID_COLUMN));
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
            statement.setInt(7, entity.getId());
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
        return deleteById(entity.getId());
    }

    @Override
    public List<DailyTask> findByHeading(String heading) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_BY_HEADING_SQL)) {
            statement.setString(1, heading.toLowerCase());

            return getDailyTaskList(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DailyTask> findAllByUserId(Integer userId) {
        try (var connection = ConnectionManager.get();
        var statement =  connection.prepareStatement(FIND_BY_USER_ID_SQL)) {
            statement.setInt(1, userId);

            return getDailyTaskList(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<DailyTask> getDailyTaskList(PreparedStatement statement) throws SQLException {
        var resultSet = statement.executeQuery();
        List<DailyTask> tasks = new ArrayList<>();
        while (resultSet.next()) {
            DailyTask task = buildDailyTask(resultSet);
            tasks.add(task);
        }
        return tasks;
    }

    public DailyTask buildDailyTask(ResultSet resultSet) throws SQLException {
        return DailyTask.builder()
                .id(resultSet.getInt(ID_COLUMN))
                .heading(resultSet.getString(HEADING_COLUMN))
                .description(resultSet.getString(DESCRIPTION_COLUMN))
                .complexity(Complexity.valueOf(resultSet.getString(COMPLEXITY_COLUMN)))
                .deadline(resultSet.getDate(DEADLINE_COLUMN).toLocalDate())
                .status(resultSet.getBoolean(STATUS_COLUMN))
                .series(resultSet.getInt(SERIES_COLUMN))
                .userId(resultSet.getInt(USER_ID_COLUMN))
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
