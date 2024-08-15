package com.pulsar.habitica.dao.user;

import com.pulsar.habitica.dao.table.UserStatisticsTable;
import com.pulsar.habitica.entity.user.UserStatistics;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.pulsar.habitica.dao.table.UserStatisticsTable.*;

public class UserStatisticsDaoImpl implements UserStatisticsDao {

    private static final String FIND_BY_USER_ID_SQL = "SELECT * FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, USER_ID);
    private static final String UPDATE_SQL = "UPDATE %s SET %s = ? WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, TOTAL_VISITS, USER_ID);
    private volatile static UserStatisticsDaoImpl INSTANCE;

    private UserStatisticsDaoImpl() {}

    @Override
    public Optional<UserStatistics> findByUserId(Integer id) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_BY_USER_ID_SQL)) {
            statement.setInt(1, id);
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
    public void update(UserStatistics userStatistics) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setInt(1, userStatistics.getTotalVisits());
            statement.setInt(2, userStatistics.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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