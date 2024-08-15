package com.pulsar.habitica.dao.user;

import com.pulsar.habitica.entity.user.UserStatistics;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserStatisticsDaoImpl implements UserStatisticsDao {

    private static final String FIND_BY_USER_ID_SQL = "SELECT * FROM users.user_statistics WHERE user_id = ?";
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

    }

    private UserStatistics buildUserStatistics(ResultSet resultSet) throws SQLException {
        return UserStatistics.builder()
                .userId(resultSet.getInt("user_id"))
                .totalVisits(resultSet.getInt("total_visits"))
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
