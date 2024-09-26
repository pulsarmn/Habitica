package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.task.TaskDao;
import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.entity.task.Task;
import com.pulsar.habitica.mapper.Mapper;
import com.pulsar.habitica.mapper.TaskDtoMapper;

import java.util.List;
import java.util.NoSuchElementException;

public class TaskService {

    private final TaskDao<Task> taskDao;
    private final Mapper<TaskDto, Task> dtoTaskMapper = TaskDtoMapper.getInstance();

    public TaskService(TaskDao<Task> taskDao) {
        this.taskDao = taskDao;
    }

    public Task createTask(TaskDto taskDto) {
        var task = dtoTaskMapper.mapFrom(taskDto);
        return taskDao.save(task);
    }

    public Task findById(int taskId) {
        return taskDao.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("The task with id 3 was not found!"));
    }

    public List<Task> findAllByUserId(int userId) {
        return taskDao.findAllByUserId(userId);
    }

    public boolean deleteTask(int taskId) {
        return taskDao.deleteById(taskId);
    }
}
