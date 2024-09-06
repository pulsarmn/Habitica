package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.task.TaskDao;
import com.pulsar.habitica.entity.task.Task;

public class TaskService {

    private final TaskDao<Task> taskDao;

    public TaskService(TaskDao<Task> taskDao) {
        this.taskDao = taskDao;
    }
}
