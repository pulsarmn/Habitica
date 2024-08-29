package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.user.UserBalanceDao;
import com.pulsar.habitica.entity.user.User;
import com.pulsar.habitica.entity.user.UserBalance;

public class UserBalanceService {

    private final UserBalanceDao userBalanceDao;

    public UserBalanceService(UserBalanceDao userBalanceDao) {
        this.userBalanceDao = userBalanceDao;
    }

    public UserBalance initUserBalance(User user) {
        return userBalanceDao.init(user.getId());
    }
}
