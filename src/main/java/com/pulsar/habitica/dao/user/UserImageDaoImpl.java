package com.pulsar.habitica.dao.user;

import com.pulsar.habitica.entity.user.UserImage;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserImageDaoImpl implements UserImageDao {

    private static final String FIND_ALL_BY_USER_ID_SQL = "SELECT * FROM users.user_images WHERE user_id = ?";
    private static final String SAVE_SQL = "INSERT INTO users.user_images VALUES (?, ?)";
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
    public boolean save(UserImage entity) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(SAVE_SQL)) {
            statement.setInt(1, entity.getUserId());
            statement.setString(2, entity.getImageAddr());
            int status = statement.executeUpdate();
            return status > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(UserImage entity) {
        return false;
    }

    private UserImage buildUserImage(ResultSet resultSet) throws SQLException {
        return UserImage.builder()
                .userId(resultSet.getInt("user_id"))
                .imageAddr(resultSet.getString("image_addr"))
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
