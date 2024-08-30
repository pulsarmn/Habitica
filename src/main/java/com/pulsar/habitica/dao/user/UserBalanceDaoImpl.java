package com.pulsar.habitica.dao.user;

import com.pulsar.habitica.entity.user.UserBalance;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pulsar.habitica.dao.table.UserBalanceTable.*;

public class UserBalanceDaoImpl implements UserBalanceDao {

    private static final String FIND_ALL_SQL = "SELECT * FROM %s"
            .formatted(FULL_TABLE_NAME);
    private static final String FIND_BY_USER_ID_SQL = "SELECT * FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, USER_ID);
    private static final String UPDATE_SQL = "UPDATE %s SET %s = ? WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, USER_BALANCE, USER_ID);
    private static final String SAVE_SQL = "INSERT INTO %s (%s, %s) VALUES (?, ?) RETURNING *"
            .formatted(FULL_TABLE_NAME, USER_ID, USER_BALANCE);
    private static final String DELETE_BY_USER_ID_SQL = "DELETE FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, USER_ID);
    private volatile static UserBalanceDaoImpl INSTANCE;

    private UserBalanceDaoImpl() {
    }

    @Override
    public List<UserBalance> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = statement.executeQuery();
            List<UserBalance> userBalances = new ArrayList<>();
            while (resultSet.next()) {
                var userBalance = buildUserBalance(resultSet);
                userBalances.add(userBalance);
            }
            return userBalances;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UserBalance> findById(Integer userId) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_USER_ID_SQL)) {
            statement.setInt(1, userId);
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
    public UserBalance save(UserBalance userBalance) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL)) {
            statement.setInt(1, userBalance.getUserId());
            statement.setBigDecimal(2, userBalance.getBalance());
            var resultSet = statement.executeQuery();
            resultSet.next();
            return buildUserBalance(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserBalance update(UserBalance userBalance) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setBigDecimal(1, userBalance.getBalance());
            statement.setInt(2, userBalance.getUserId());
            statement.executeUpdate();
            return userBalance;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public boolean delete(UserBalance entity) {
        return false;
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