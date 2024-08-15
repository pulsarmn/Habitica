package com.pulsar.habitica.dao.user;

import com.pulsar.habitica.entity.user.User;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pulsar.habitica.dao.table.UserTable.*;
import static java.sql.Statement.*;

public class UserDaoImpl implements UserDao {

    private static final String FIND_ALL_SQL = "SELECT * FROM %s"
            .formatted(FULL_TABLE_NAME);
    private static final String FIND_BY_ID_SQL = "SELECT * FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, ID_COLUMN);
    private static final String SAVE_SQL = "INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)"
            .formatted(FULL_TABLE_NAME, EMAIL_COLUMN, PASSWORD_COLUMN, NICKNAME_COLUMN);
    private static final String UPDATE_SQL = "UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, EMAIL_COLUMN, PASSWORD_COLUMN, NICKNAME_COLUMN, ID_COLUMN);
    private static final String DELETE_BY_ID_SQL = "DELETE FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, ID_COLUMN);
    private static final String FIND_BY_EMAIL_SQL = "SELECT * FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, EMAIL_COLUMN);
    private static final String FIND_BY_NICKNAME_SQL = "SELECT * FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, NICKNAME_COLUMN);
    private static UserDaoImpl INSTANCE;

    private UserDaoImpl() {}

    @Override
    public List<User> findAll() {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = buildUser(resultSet);
                users.add(user);
            }
            return users;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            var resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = buildUser(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User save(User entity) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            setUserParameters(statement, entity);
            statement.executeUpdate();

            var keys = statement.getGeneratedKeys();
            keys.next();
            entity.setId(keys.getInt(ID_COLUMN));
            entity.setCreatedAt(keys.getDate(CREATED_AT_COLUMN).toLocalDate());
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User update(User entity) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(UPDATE_SQL)) {
            setUserParameters(statement, entity);
            statement.setInt(4, entity.getId());
            statement.executeUpdate();

            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            statement.setInt(1, id);
            int status = statement.executeUpdate();
            return status == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(User entity) {
        return deleteById(entity.getId());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return findByParameter(email, FIND_BY_EMAIL_SQL);
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return findByParameter(nickname, FIND_BY_NICKNAME_SQL);
    }

    private Optional<User> findByParameter(String emailOrNickname, String query) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(query)) {
            statement.setString(1, emailOrNickname);

            var resultSet = statement.executeQuery();
            User foundUser = null;
            if (resultSet.next()) {
                foundUser = buildUser(resultSet);
            }
            return Optional.ofNullable(foundUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt(ID_COLUMN))
                .email(resultSet.getString(EMAIL_COLUMN))
                .password(resultSet.getString(PASSWORD_COLUMN))
                .nickname(resultSet.getString(NICKNAME_COLUMN))
                .createdAt(resultSet.getDate(CREATED_AT_COLUMN).toLocalDate())
                .build();
    }

    private void setUserParameters(PreparedStatement statement, User entity) throws SQLException {
        statement.setString(1, entity.getEmail());
        statement.setString(2, entity.getPassword());
        statement.setString(3, entity.getNickname());
    }

    public static UserDaoImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (UserDaoImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserDaoImpl();
                }
            }
        }
        return INSTANCE;
    }
}