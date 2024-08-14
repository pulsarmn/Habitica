package com.pulsar.habitica.dao;

import com.pulsar.habitica.entity.task.TaskBase;

import java.util.List;

public interface TaskDao<T extends TaskBase> extends Dao<Integer, T> {

    List<T> findByHeading(String heading);

    List<T> findAllByUserId(Integer userId);
}
