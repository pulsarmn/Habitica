package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.user.UserBalanceDao;
import com.pulsar.habitica.entity.user.UserBalance;
import com.pulsar.habitica.exception.InsufficientBalanceException;

import java.math.BigDecimal;
import java.sql.SQLException;
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

    public boolean isPurchased(int userId, BigDecimal cost) {
        if (cost == null) {
            throw new IllegalArgumentException("illegal cost - " + cost);
        }

        var userBalance = findUserBalance(userId);

        BigDecimal balance = userBalance.getBalance();
        if (balance.compareTo(cost) < 0) {
            throw new InsufficientBalanceException("There are not enough funds to perform operation.");
        }

        BigDecimal newBalance = balance.subtract(cost);
        userBalance.setBalance(newBalance);
        updateUserBalance(userId, newBalance);

        return true;
    }
}
