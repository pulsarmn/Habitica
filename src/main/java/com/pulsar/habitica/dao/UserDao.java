package com.pulsar.habitica.dao;

import com.pulsar.habitica.entity.User;

import java.util.Optional;

public interface UserDao extends Dao<Integer, User> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);
}
