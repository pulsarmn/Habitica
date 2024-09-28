package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.task.TaskDao;
import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.entity.task.Complexity;
import com.pulsar.habitica.entity.task.DailyTask;
import com.pulsar.habitica.exception.UnauthorizedException;
import com.pulsar.habitica.mapper.DailyTaskDtoMapper;
import com.pulsar.habitica.mapper.Mapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;

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

    public DailyTask findById(int dailyTaskId) {
        return taskDao.findById(dailyTaskId).orElseThrow(() -> new NoSuchElementException("The daily task with id " + dailyTaskId + " was not found!"));
    }

    public List<DailyTask> findAllByUserId(int userId) {
        return taskDao.findAllByUserId(userId);
    }

    public DailyTask update(DailyTask dailyTask) {
        return taskDao.update(dailyTask);
    }

    public DailyTask updateDailyTask(int userId, JSONObject dailyTaskData) {
        if (dailyTaskData == null || dailyTaskData.isEmpty()) {
            throw new JSONException("JSON is invalid or empty!");
        }

        int dailyTaskId = dailyTaskData.getInt("id");
        DailyTask dailyTask = findById(dailyTaskId);

        if (dailyTask.getUserId() != userId) {
            throw new UnauthorizedException();
        }

        LocalDate dailyTaskStart = dailyTask.getDeadline();
        try {
            dailyTaskStart = LocalDate.parse(dailyTaskData.getString("deadline"));
        }catch (DateTimeParseException e) {

        }

        dailyTask.setHeading(dailyTaskData.getString("heading"));
        dailyTask.setDescription(dailyTaskData.getString("description"));
        dailyTask.setComplexity(Complexity.valueOf(dailyTaskData.getString("complexity")));
        dailyTask.setDeadline(dailyTaskStart);

        return taskDao.update(dailyTask);
    }

    public boolean deleteDailyTask(int dailyTaskId) {
        return taskDao.deleteById(dailyTaskId);
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
