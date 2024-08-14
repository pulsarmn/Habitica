package com.pulsar.habitica.dao.user;

import com.pulsar.habitica.dao.Dao;
import com.pulsar.habitica.entity.user.User;

import java.util.Optional;

public interface UserDao extends Dao<Integer, User> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);
}
