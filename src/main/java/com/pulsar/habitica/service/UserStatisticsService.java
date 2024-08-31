package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.user.UserStatisticsDao;

public class UserStatisticsService {

    private final UserStatisticsDao userStatisticsDao;

    public UserStatisticsService(UserStatisticsDao userStatisticsDao) {
        this.userStatisticsDao = userStatisticsDao;
    }
}
