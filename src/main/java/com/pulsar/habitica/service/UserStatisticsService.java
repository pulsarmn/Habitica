package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.user.UserStatisticsDao;
import com.pulsar.habitica.entity.user.UserStatistics;

import java.util.Optional;

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

    public UserStatistics updateUserStatistics(int userId, int totalVisits) {
        return userStatisticsDao.update(UserStatistics.builder()
                .userId(userId)
                .totalVisits(totalVisits)
                .build());
    }

    public UserStatistics findUserStatistics(int userId) {
        return userStatisticsDao.findById(userId).get();
    }
}