package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.task.TaskDao;
import com.pulsar.habitica.entity.task.DailyTask;

public class DailyTaskService {

    private final TaskDao<DailyTask> taskDao;

    public DailyTaskService(TaskDao<DailyTask> taskDao) {
        this.taskDao = taskDao;
    }


}
