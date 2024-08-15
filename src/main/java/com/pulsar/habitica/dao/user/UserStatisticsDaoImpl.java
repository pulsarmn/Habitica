package com.pulsar.habitica.dao.user;

import com.pulsar.habitica.entity.user.UserStatistics;

import java.util.Optional;

public class UserStatisticsDaoImpl implements UserStatisticsDao {

    private volatile static UserStatisticsDaoImpl INSTANCE;

    private UserStatisticsDaoImpl() {}

    @Override
    public Optional<UserStatistics> findByUserId(Integer id) {
        return Optional.empty();
    }

    @Override
    public void update(UserStatistics userStatistics) {

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
