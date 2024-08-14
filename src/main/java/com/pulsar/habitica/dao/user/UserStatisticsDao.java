package com.pulsar.habitica.dao.user;

import com.pulsar.habitica.entity.user.UserStatistics;

import java.util.Optional;

public interface UserStatisticsDao {

    Optional<UserStatistics> findByUserId(Integer id);

    void update(UserStatistics userStatistics);
}
