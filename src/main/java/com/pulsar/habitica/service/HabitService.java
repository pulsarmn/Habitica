package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.task.TaskDao;
import com.pulsar.habitica.entity.task.Habit;

public class HabitService {

    private TaskDao<Habit> habitDao;

    public HabitService(TaskDao<Habit> habitDao) {
        this.habitDao = habitDao;
    }
}
