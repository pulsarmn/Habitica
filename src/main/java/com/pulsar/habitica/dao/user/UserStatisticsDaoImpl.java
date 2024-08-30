package com.pulsar.habitica.dao.user;

import com.pulsar.habitica.entity.user.UserStatistics;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pulsar.habitica.dao.table.UserStatisticsTable.*;

public class UserStatisticsDaoImpl implements UserStatisticsDao {

    private static final String FIND_ALL_SQL = "SELECT * FROM %s"
            .formatted(FULL_TABLE_NAME);
    private static final String FIND_BY_USER_ID_SQL = "SELECT * FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, USER_ID);
    private static final String SAVE_SQL = "INSERT INTO %s VALUES (?, ?) RETURNING *"
            .formatted(FULL_TABLE_NAME);
    private static final String UPDATE_SQL = "UPDATE %s SET %s = ? WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, TOTAL_VISITS, USER_ID);
    private static final String DELETE_BY_ID_SQL = "DELETE FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, USER_ID);
    private volatile static UserStatisticsDaoImpl INSTANCE;

    private UserStatisticsDaoImpl() {}

    @Override
    public List<UserStatistics> findAll() {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = statement.executeQuery();
            List<UserStatistics> userStatistics = new ArrayList<>();
            while (resultSet.next()) {
                var statistics = buildUserStatistics(resultSet);
                userStatistics.add(statistics);
            }
            return userStatistics;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UserStatistics> findById(Integer userId) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_BY_USER_ID_SQL)) {
            statement.setInt(1, userId);
            var resultSet = statement.executeQuery();
            UserStatistics userStatistics = null;
            if (resultSet.next()) {
                userStatistics = buildUserStatistics(resultSet);
            }
            return Optional.ofNullable(userStatistics);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserStatistics save(UserStatistics entity) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(SAVE_SQL)) {
            statement.setInt(1, entity.getUserId());
            statement.setInt(2, entity.getTotalVisits());
            var resultSet = statement.executeQuery();
            return buildUserStatistics(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserStatistics update(UserStatistics entity) {
        try (var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setInt(1, entity.getTotalVisits());
            statement.setInt(2, entity.getUserId());
            statement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Integer userId) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            statement.setInt(1, userId);
            int status = statement.executeUpdate();
            return status > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(UserStatistics entity) {
        return deleteById(entity.getUserId());
    }

    private UserStatistics buildUserStatistics(ResultSet resultSet) throws SQLException {
        return UserStatistics.builder()
                .userId(resultSet.getInt(USER_ID))
                .totalVisits(resultSet.getInt(TOTAL_VISITS))
                .build();
    }

    public static UserStatisticsDaoImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (UserStatisticsDaoImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserStatisticsDaoImpl();
                }
            }
        }
        return INSTANCE;
    }
}