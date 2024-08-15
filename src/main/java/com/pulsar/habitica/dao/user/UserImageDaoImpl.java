package com.pulsar.habitica.dao.user;

import com.pulsar.habitica.entity.user.UserImage;

import java.util.List;

public class UserImageDaoImpl implements UserImageDao {

    private volatile static UserImageDaoImpl INSTANCE;

    private UserImageDaoImpl() {}

    @Override
    public List<UserImage> findAllByUserId(Integer id) {
        return null;
    }

    @Override
    public boolean save(UserImage userImage) {
        return false;
    }

    @Override
    public boolean delete(UserImage userImage) {
        return false;
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
