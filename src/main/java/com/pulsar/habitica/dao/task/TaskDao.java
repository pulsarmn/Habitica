package com.pulsar.habitica.dao.task;

import com.pulsar.habitica.dao.Dao;
import com.pulsar.habitica.entity.task.TaskBase;

import java.util.List;

public interface TaskDao<T extends TaskBase> extends Dao<Integer, T> {

    List<T> findByHeading(String heading);

    List<T> findAllByUserId(Integer userId);
}
