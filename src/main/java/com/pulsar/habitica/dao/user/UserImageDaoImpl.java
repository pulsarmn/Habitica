package com.pulsar.habitica.dao.user;

import com.pulsar.habitica.entity.user.UserImage;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pulsar.habitica.dao.table.UserImageTable.*;

public class UserImageDaoImpl implements UserImageDao {

    private static final String FIND_ALL_SQL = "SELECT * FROM %s"
            .formatted(FULL_TABLE_NAME);
    private static final String FIND_BY_USER_ID_SQL = "SELECT * FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, USER_ID);
    private static final String SAVE_SQL = "INSERT INTO %s VALUES (?, ?) RETURNING *"
            .formatted(FULL_TABLE_NAME);
    private static final String UPDATE_SQL = "UPDATE %s SET %s = ? WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, IMAGE_ADDRESS, USER_ID);
    private static final String DELETE_BY_ID_SQL = "DELETE FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, USER_ID);
    private static final String DELETE_SQL = "DELETE FROM %s WHERE %s = ? AND %s = ?"
            .formatted(FULL_TABLE_NAME, USER_ID, IMAGE_ADDRESS);
    private volatile static UserImageDaoImpl INSTANCE;

    private UserImageDaoImpl() {}

    @Override
    public List<UserImage> findAll() {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = statement.executeQuery();
            List<UserImage> images = new ArrayList<>();
            while (resultSet.next()) {
                var userImage = buildUserImage(resultSet);
                images.add(userImage);
            }
            return images;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UserImage> findById(Integer userId) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_BY_USER_ID_SQL)) {
            var resultSet = statement.executeQuery();
            UserImage userImage = null;
            if (resultSet.next()) {
                userImage = buildUserImage(resultSet);
            }
            return Optional.ofNullable(userImage);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserImage save(UserImage entity) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(SAVE_SQL)) {
            statement.setInt(1, entity.getUserId());
            statement.setString(2, entity.getImageAddr());
            var resultSet = statement.executeQuery();
            resultSet.next();
            return buildUserImage(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserImage update(UserImage entity) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, entity.getImageAddr());
            statement.setInt(2, entity.getUserId());
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
            return status > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(UserImage entity) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, entity.getUserId());
            statement.setString(2, entity.getImageAddr());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private UserImage buildUserImage(ResultSet resultSet) throws SQLException {
        return UserImage.builder()
                .userId(resultSet.getInt(USER_ID))
                .imageAddr(resultSet.getString(IMAGE_ADDRESS))
                .build();
    }

    public static UserImageDaoImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (UserImageDaoImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserImageDaoImpl();
                }
            }
        }
        return INSTANCE;
    }
}