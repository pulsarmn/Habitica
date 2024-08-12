package com.pulsar.habitica.dao;

import com.pulsar.habitica.entity.Task;
import com.pulsar.habitica.entity.TaskBase;

import java.util.List;
import java.util.Optional;

public interface TaskDao<T extends TaskBase> extends Dao<Integer, T> {

    List<T> findByHeading(String heading);

    List<T> findAllByUserId(Integer userId);
}
