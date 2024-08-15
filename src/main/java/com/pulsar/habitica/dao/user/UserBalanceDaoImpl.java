package com.pulsar.habitica.dao.user;

import com.pulsar.habitica.entity.user.UserBalance;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserBalanceDaoImpl implements UserBalanceDao {

    private static final String FIND_BY_USER_ID_SQL = "SELECT * FROM users.user_balances";
    private static final String UPDATE_SQL = "UPDATE users.user_balances SET balance = ? WHERE user_id = ?";
    private volatile static UserBalanceDaoImpl INSTANCE;

    private UserBalanceDaoImpl() {
    }

    @Override
    public Optional<UserBalance> findByUserId(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_USER_ID_SQL)) {
            var resultSet = statement.executeQuery();
            UserBalance userBalance = null;
            if (resultSet.next()) {
                userBalance = buildUserBalance(resultSet);
            }
            return Optional.ofNullable(userBalance);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(UserBalance userBalance) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setBigDecimal(1, userBalance.getBalance());
            statement.setInt(2, userBalance.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private UserBalance buildUserBalance(ResultSet resultSet) throws SQLException {
        return UserBalance.builder()
                .userId(resultSet.getInt("user_id"))
                .balance(resultSet.getBigDecimal("balance"))
                .build();
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