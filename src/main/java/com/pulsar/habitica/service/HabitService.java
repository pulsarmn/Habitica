package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.task.TaskDao;
import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.entity.task.Habit;
import com.pulsar.habitica.mapper.HabitDtoMapper;
import com.pulsar.habitica.mapper.Mapper;

import java.util.List;

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
                .orElse(Habit.builder()
                        .id(habitId)
                        .build());
    }

    public List<Habit> findAllByUserId(int userId) {
        return habitDao.findAllByUserId(userId);
    }

    public Habit update(Habit habit) {
        return habitDao.update(habit);
    }

    public Habit incrementGoodSeries(int habitId) {
        var habit = findById(habitId);
        var goodSeries = habit.getGoodSeries();
        habit.setGoodSeries(++goodSeries);
        return habitDao.update(habit);
    }
}
