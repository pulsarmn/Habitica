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

    public List<DailyTask> findAll() {
        return taskDao.findAll();
    }

    public DailyTask findById(int id) {
        return taskDao.findById(id).orElse(
                DailyTask.builder()
                        .id(id)
                        .build());
    }

    public List<DailyTask> findAllByUserId(int userId) {
        return taskDao.findAllByUserId(userId);
    }

    public DailyTask incrementSeries(int dailyTaskId) {
        var dailyTask = findById(dailyTaskId);
        dailyTask.setSeries(dailyTask.getSeries() + 1);
        dailyTask.setStatus(true);
        return taskDao.update(dailyTask);
    }

    public DailyTask decrementSeries(int dailyTaskId) {
        var dailyTask = findById(dailyTaskId);
        int currentSeries = dailyTask.getSeries();
        if (currentSeries > 0) {
            dailyTask.setSeries(currentSeries - 1);
            dailyTask.setStatus(false);
        }
        return taskDao.update(dailyTask);
    }
}
