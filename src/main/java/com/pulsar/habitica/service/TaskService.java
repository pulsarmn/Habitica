package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.task.TaskDao;
import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.entity.task.Complexity;
import com.pulsar.habitica.entity.task.Task;
import com.pulsar.habitica.exception.UnauthorizedException;
import com.pulsar.habitica.mapper.Mapper;
import com.pulsar.habitica.mapper.TaskDtoMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
                .orElseThrow(() -> new NoSuchElementException("The task with id " + taskId + " was not found!"));
    }

    public List<Task> findAllByUserId(int userId) {
        return taskDao.findAllByUserId(userId);
    }

    public Task updateTask(int userId, JSONObject taskData) {
        if (taskData == null || taskData.isEmpty()) {
            throw new JSONException("JSON is invalid or empty");
        }

        int taskId = taskData.getInt("id");
        Task task = findById(taskId);

        if (task.getUserId() != userId) {
            throw new UnauthorizedException();
        }

        LocalDate taskDeadline;
        try {
            taskDeadline = LocalDate.parse(taskData.getString("deadline"));
        }catch (DateTimeParseException e) {
            taskDeadline = null;
        }

        task.setHeading(taskData.getString("heading"));
        task.setDescription(taskData.getString("description"));
        task.setComplexity(Complexity.valueOf(taskData.getString("complexity")));
        task.setDeadline(taskDeadline);

        return taskDao.update(task);
    }

    public boolean deleteTask(int taskId) {
        return taskDao.deleteById(taskId);
    }
}
