package com.pulsar.habitica.dao;

import com.pulsar.habitica.entity.Complexity;
import com.pulsar.habitica.entity.Habit;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pulsar.habitica.dao.table.HabitTable.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class HabitDaoImpl implements TaskDao<Habit> {

    private static final String FIND_ALL_SQL = "SELECT * FROM %s"
            .formatted(FULL_TABLE_NAME);
    private static final String FIND_BY_ID_SQL = "SELECT * FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, ID_COLUMN);
    private static final String SAVE_SQL = "INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)"
            .formatted(FULL_TABLE_NAME,
                    HEADING_COLUMN,
                    DESCRIPTION_COLUMN,
                    COMPLEXITY_COLUMN,
                    BAD_SERIES_COLUMN,
                    GOOD_SERIES_COLUMN,
                    USER_ID_COLUMN);
    private static final String UPDATE_SQL = "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?"
            .formatted(FULL_TABLE_NAME,
                    HEADING_COLUMN,
                    DESCRIPTION_COLUMN,
                    COMPLEXITY_COLUMN,
                    BAD_SERIES_COLUMN,
                    GOOD_SERIES_COLUMN,
                    ID_COLUMN);
    private static final String DELETE_BY_ID_SQL = "DELETE FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, ID_COLUMN);
    private static final String FIND_BY_HEADING_SQL = "SELECT * FROM %s WHERE LOWER(%s) LIKE CONCAT('%', ?, '%')"
            .replaceFirst("%s", FULL_TABLE_NAME).replaceFirst("%s", HEADING_COLUMN);
    private static final String FIND_BY_USER_ID_SQL = "SELECT * FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, USER_ID_COLUMN);
    private static final HabitDaoImpl INSTANCE = new HabitDaoImpl();

    private HabitDaoImpl() {
    }

    @Override
    public List<Habit> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            return getHabitsList(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Habit> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            var resultSet = statement.executeQuery();
            Habit habit = null;
            if (resultSet.next()) {
                habit = buildHabit(resultSet);
            }
            return Optional.ofNullable(habit);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Habit save(Habit entity) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            setHabitParameters(statement, entity);
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
    public Habit update(Habit entity) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            setHabitParameters(statement, entity);
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
    public boolean delete(Habit entity) {
        return deleteById(entity.getId());
    }

    @Override
    public List<Habit> findByHeading(String heading) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_HEADING_SQL)) {
            statement.setString(1, heading.toLowerCase());

            return getHabitsList(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Habit> findAllByUserId(Integer userId) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_USER_ID_SQL)) {
            statement.setInt(1, userId);

            return getHabitsList(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Habit> getHabitsList(PreparedStatement statement) throws SQLException {
        var resultSet = statement.executeQuery();
        List<Habit> habits = new ArrayList<>();
        while (resultSet.next()) {
            Habit habit = buildHabit(resultSet);
            habits.add(habit);
        }
        return habits;
    }

    private Habit buildHabit(ResultSet resultSet) throws SQLException {
        return Habit.builder()
                .id(resultSet.getInt(ID_COLUMN))
                .heading(resultSet.getString(HEADING_COLUMN))
                .description(resultSet.getString(DESCRIPTION_COLUMN))
                .complexity(Complexity.valueOf(resultSet.getString(COMPLEXITY_COLUMN)))
                .badSeries(resultSet.getInt(BAD_SERIES_COLUMN))
                .goodSeries(resultSet.getInt(GOOD_SERIES_COLUMN))
                .userId(resultSet.getInt(USER_ID_COLUMN))
                .build();
    }

    private void setHabitParameters(PreparedStatement statement, Habit entity) throws SQLException {
        statement.setString(1, entity.getHeading());
        statement.setString(2, entity.getDescription());
        statement.setString(3, entity.getComplexity().name());
        statement.setInt(4, entity.getBadSeries());
        statement.setInt(5, entity.getGoodSeries());
    }

    public static HabitDaoImpl getInstance() {
        return INSTANCE;
    }
}