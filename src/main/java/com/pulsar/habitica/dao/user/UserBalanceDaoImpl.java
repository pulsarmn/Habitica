package com.pulsar.habitica.dao.user;

import com.pulsar.habitica.dao.table.UserBalanceTable;
import com.pulsar.habitica.entity.user.UserBalance;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.pulsar.habitica.dao.table.UserBalanceTable.*;

public class UserBalanceDaoImpl implements UserBalanceDao {

    private static final String FIND_BY_USER_ID_SQL = "SELECT * FROM %s"
            .formatted(FULL_TABLE_NAME);
    private static final String UPDATE_SQL = "UPDATE %s SET %s = ? WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, USER_BALANCE, USER_ID);
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
                .userId(resultSet.getInt(USER_ID))
                .balance(resultSet.getBigDecimal(USER_BALANCE))
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