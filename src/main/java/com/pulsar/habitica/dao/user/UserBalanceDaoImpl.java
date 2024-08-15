package com.pulsar.habitica.dao.user;

import com.pulsar.habitica.entity.user.UserBalance;

import java.util.Optional;

public class UserBalanceDaoImpl implements UserBalanceDao {

    private volatile static UserBalanceDaoImpl INSTANCE;

    private UserBalanceDaoImpl() {
    }

    @Override
    public Optional<UserBalance> findByUserId(Integer id) {
        return null;
    }

    @Override
    public void update(UserBalance userBalance) {

    }

    public static UserBalanceDaoImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (UserBalanceDaoImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserBalanceDaoImpl();
                }
            }
        }
        return INSTANCE;
    }
}