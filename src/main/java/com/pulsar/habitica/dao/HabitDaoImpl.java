package com.pulsar.habitica.dao;

import com.pulsar.habitica.entity.Complexity;
import com.pulsar.habitica.entity.Habit;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HabitDaoImpl implements TaskDao<Habit> {

    private static final String FIND_ALL_SQL = "SELECT * FROM task.habit";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM task.habit WHERE id = ?";
    private static final HabitDaoImpl INSTANCE = new HabitDaoImpl();

    private HabitDaoImpl() {}

    @Override
    public List<Habit> findAll() {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = statement.executeQuery();
            List<Habit> habits = new ArrayList<>();
            while (resultSet.next()) {
                Habit habit = buildHabit(resultSet);
                habits.add(habit);
            }
            return habits;
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

    public Habit buildHabit(ResultSet resultSet) throws SQLException {
        return Habit.builder()
                .id(resultSet.getInt("id"))
                .heading(resultSet.getString("heading"))
                .description(resultSet.getString("description"))
                .complexity(Complexity.valueOf(resultSet.getString("complexity")))
                .badSeries(resultSet.getInt("bad_series"))
                .goodSeries(resultSet.getInt("good_series"))
                .userId(resultSet.getInt("user_id"))
                .build();
    }

    public static HabitDaoImpl getInstance() {
        return INSTANCE;
    }
}
