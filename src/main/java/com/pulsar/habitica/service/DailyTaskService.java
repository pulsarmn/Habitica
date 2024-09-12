package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.task.TaskDao;
import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.entity.task.DailyTask;
import com.pulsar.habitica.mapper.DailyTaskDtoMapper;
import com.pulsar.habitica.mapper.Mapper;

import java.util.List;

public class DailyTaskService {

    private final TaskDao<DailyTask> taskDao;
    private final Mapper<TaskDto, DailyTask> dtoDailyTaskMapper = DailyTaskDtoMapper.getInstance();

    public DailyTaskService(TaskDao<DailyTask> taskDao) {
        this.taskDao = taskDao;
    }

    public DailyTask createDailyTask(TaskDto taskDto) {
        var dailyTask = dtoDailyTaskMapper.mapFrom(taskDto);
        return taskDao.save(dailyTask);
    }

    public DailyTask findById(int id) {
        return taskDao.findById(id).orElse(
                DailyTask.builder()
                        .id(id)
                        .build());
    }
}
