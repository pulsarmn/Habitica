package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.user.UserBalanceDao;
import com.pulsar.habitica.dto.UserDto;
import com.pulsar.habitica.entity.user.User;
import com.pulsar.habitica.entity.user.UserBalance;

import java.math.BigDecimal;

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
}
