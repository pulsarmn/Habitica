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

    private static final String FIND_ALL_BY_USER_ID_SQL = "SELECT * FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, USER_ID);
    private static final String SAVE_SQL = "INSERT INTO %s VALUES (?, ?) RETURNING *"
            .formatted(FULL_TABLE_NAME);
    private static final String DELETE_SQL = "DELETE FROM %s WHERE %s = ? AND %s = ?"
            .formatted(FULL_TABLE_NAME, USER_ID, IMAGE_ADDRESS);
    private volatile static UserImageDaoImpl INSTANCE;

    private UserImageDaoImpl() {}

    @Override
    public List<UserImage> findAllByUserId(Integer id) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_ALL_BY_USER_ID_SQL)) {
            statement.setInt(1, id);
            var resultSet = statement.executeQuery();
            List<UserImage> images = new ArrayList<>();
            while (resultSet.next()) {
                UserImage userImage = buildUserImage(resultSet);
                images.add(userImage);
            }
            return images;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserImage> findAll() {
        return null;
    }

    @Override
    public Optional<UserImage> findById(Integer id) {
        return Optional.empty();
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
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
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