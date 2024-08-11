package com.pulsar.habitica.dao;

import com.pulsar.habitica.entity.User;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.*;

public class UserDaoImpl implements UserDao {

    private static final String FIND_ALL_SQL = "SELECT * FROM users.users";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM users.users WHERE id = ?";
    private static final String SAVE_SQL = "INSERT INTO users.users (email, password, nickname) VALUES (?, ?, ?)";
    private static final String UPDATE_SQL = """
            UPDATE users.users
            SET email = ?,
            password = ?,
            nickname = ?
            WHERE id = ?
            """;
    private static final String DELETE_BY_ID_SQL = "DELETE FROM users.users WHERE id = ?";
    private static final String FIND_BY_EMAIL_SQL = "SELECT * FROM users.users WHERE email = ?";
    private static final UserDaoImpl INSTANCE = new UserDaoImpl();

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
            entity.setId(keys.getInt("id"));
            entity.setCreatedAt(keys.getDate("created_at").toLocalDate());
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
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_BY_EMAIL_SQL)) {
            statement.setString(1, email);

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

    @Override
    public Optional<User> findByNickname(String nickname) {
        return Optional.empty();
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .nickname(resultSet.getString("nickname"))
                .createdAt(resultSet.getDate("created_at").toLocalDate())
                .build();
    }

    private void setUserParameters(PreparedStatement statement, User entity) throws SQLException {
        statement.setString(1, entity.getEmail());
        statement.setString(2, entity.getPassword());
        statement.setString(3, entity.getNickname());
    }

    public static UserDaoImpl getInstance() {
        return INSTANCE;
    }
}