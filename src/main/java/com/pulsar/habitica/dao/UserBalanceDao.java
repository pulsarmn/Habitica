package com.pulsar.habitica.dao;

import com.pulsar.habitica.entity.user.UserBalance;

import java.util.Optional;

public interface UserBalanceDao {

    Optional<UserBalance> findByUserId(Integer id);

    void update(UserBalance userBalance);
}
