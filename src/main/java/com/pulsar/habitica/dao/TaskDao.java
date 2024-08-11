package com.pulsar.habitica.dao;

import com.pulsar.habitica.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskDao extends Dao<Integer, Task> {

    List<Task> findByHeading(String heading);
}
