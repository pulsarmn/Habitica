package com.pulsar.habitica.dao.user;

import com.pulsar.habitica.entity.user.UserBalance;

import java.util.Optional;

public interface UserBalanceDao {

    Optional<UserBalance> findByUserId(Integer id);

    void update(UserBalance userBalance);

    UserBalance init(Integer userId);

    boolean delete(Integer userId);
}
