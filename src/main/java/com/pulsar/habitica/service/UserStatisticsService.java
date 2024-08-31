package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.user.UserStatisticsDao;
import com.pulsar.habitica.entity.user.User;
import com.pulsar.habitica.entity.user.UserStatistics;

public class UserStatisticsService {

    private final UserStatisticsDao userStatisticsDao;

    public UserStatisticsService(UserStatisticsDao userStatisticsDao) {
        this.userStatisticsDao = userStatisticsDao;
    }

    public UserStatistics initUserStatistics(int userId) {
        return userStatisticsDao.save(UserStatistics.builder()
                .userId(userId)
                .totalVisits(0)
                .build());
    }
}
