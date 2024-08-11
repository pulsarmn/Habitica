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
            setUserStatement(statement, entity);
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

    private void setUserStatement(PreparedStatement statement, User entity) throws SQLException {
        statement.setString(1, entity.getEmail());
        statement.setString(2, entity.getPassword());
        statement.setString(3, entity.getNickname());
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public boolean delete(User entity) {
        return false;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
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

    public static UserDaoImpl getInstance() {
        return INSTANCE;
    }
}