package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.task.TaskDao;
import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.entity.task.Complexity;
import com.pulsar.habitica.entity.task.Habit;
import com.pulsar.habitica.exception.UnauthorizedException;
import com.pulsar.habitica.mapper.HabitDtoMapper;
import com.pulsar.habitica.mapper.Mapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.NoSuchElementException;

public class HabitService {

    private TaskDao<Habit> habitDao;
    private Mapper<TaskDto, Habit> dtoHabitMapper = HabitDtoMapper.getInstance();

    public HabitService(TaskDao<Habit> habitDao) {
        this.habitDao = habitDao;
    }

    public Habit createHabit(TaskDto taskDto) {
        var habit = dtoHabitMapper.mapFrom(taskDto);
        return habitDao.save(habit);
    }

    public List<Habit> findAll() {
        return habitDao.findAll();
    }

    public Habit findById(int habitId) {
        return habitDao.findById(habitId)
                .orElseThrow(() -> new NoSuchElementException("The habit with id " + habitId + " was not found!"));
    }

    public List<Habit> findAllByUserId(int userId) {
        return habitDao.findAllByUserId(userId);
    }

    public Habit update(Habit habit) {
        return habitDao.update(habit);
    }

    public Habit updateHabit(int userId, JSONObject habitData) {
        if (habitData == null || habitData.isEmpty()) {
            throw new JSONException("JSON in invalid or empty!");
        }

        int id = habitData.getInt("id");
        Habit habit = findById(id);

        if (habit.getUserId() != userId) {
            throw new UnauthorizedException();
        }

        habit.setHeading(habitData.getString("heading"));
        habit.setDescription(habitData.getString("description"));
        habit.setComplexity(Complexity.valueOf(habitData.getString("complexity")));

        return habitDao.update(habit);
    }

    public Habit resetHabit(int userId, int habitId) {
        Habit habit = findById(habitId);
        if (habit.getUserId() != userId) {
            throw new UnauthorizedException();
        }

        habit.setGoodSeries(0);
        habit.setBadSeries(0);

        return habitDao.update(habit);
    }

    public boolean deleteHabit(int habitId) {
        return habitDao.deleteById(habitId);
    }

    public Habit incrementGoodSeries(int habitId) {
        var habit = findById(habitId);
        var goodSeries = habit.getGoodSeries();
        habit.setGoodSeries(++goodSeries);
        return habitDao.update(habit);
    }

    public Habit decrementBadSeries(int habitId) {
        var habit = findById(habitId);
        var badSeries = habit.getBadSeries();
        habit.setBadSeries(++badSeries);
        return habitDao.update(habit);
    }
}