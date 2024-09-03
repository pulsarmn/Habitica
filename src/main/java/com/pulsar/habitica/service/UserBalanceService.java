package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.user.UserBalanceDao;
import com.pulsar.habitica.entity.user.UserBalance;

import java.math.BigDecimal;
import java.util.Optional;

public class UserBalanceService {

    private final UserBalanceDao userBalanceDao;

    public UserBalanceService(UserBalanceDao userBalanceDao) {
        this.userBalanceDao = userBalanceDao;
    }

    public UserBalance initUserBalance(int userId) {
        return userBalanceDao.save(UserBalance.builder()
                .userId(userId)
                .balance(BigDecimal.ZERO)
                .build());
    }

    public UserBalance updateUserBalance(int userId, BigDecimal balance) {
        return userBalanceDao.update(UserBalance.builder()
                .userId(userId)
                .balance(balance)
                .build());
    }

    public UserBalance findUserBalance(int userId) {
        return userBalanceDao.findById(userId).get();
    }
}
