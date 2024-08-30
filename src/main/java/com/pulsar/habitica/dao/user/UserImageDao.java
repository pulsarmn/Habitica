package com.pulsar.habitica.dao.user;

import com.pulsar.habitica.entity.user.UserImage;
import com.pulsar.habitica.dao.Dao;

import java.util.List;

public interface UserImageDao extends Dao<Integer, UserImage> {

    List<UserImage> findAllByUserId(Integer id);
}
