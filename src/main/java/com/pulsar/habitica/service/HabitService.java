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
}
