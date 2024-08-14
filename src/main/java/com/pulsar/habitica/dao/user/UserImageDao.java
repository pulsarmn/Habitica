package com.pulsar.habitica.dao.user;

import com.pulsar.habitica.entity.user.UserImage;

import java.util.List;

public interface UserImageDao {

    List<UserImage> findAllByUserId(Integer id);

    boolean save(UserImage userImage);

    boolean delete(UserImage userImage);
}
