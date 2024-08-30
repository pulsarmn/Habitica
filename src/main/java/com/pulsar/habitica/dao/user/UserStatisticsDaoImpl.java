package com.pulsar.habitica.dao.user;

import com.pulsar.habitica.dao.table.UserStatisticsTable;
import com.pulsar.habitica.entity.user.UserStatistics;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
    public List<UserStatistics> findAll() {
        return null;
    }

    @Override
    public Optional<UserStatistics> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public UserStatistics save(UserStatistics entity) {
        return null;
    }

    @Override
    public UserStatistics update(UserStatistics entity) {
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public boolean delete(UserStatistics entity) {
        return false;
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