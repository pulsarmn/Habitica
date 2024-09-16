package com.pulsar.habitica.mapper;

import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.entity.task.Complexity;
import com.pulsar.habitica.entity.task.Habit;

public class HabitDtoMapper implements Mapper<TaskDto, Habit> {

    private static HabitDtoMapper INSTANCE;

    private HabitDtoMapper() {}

    @Override
    public Habit mapFrom(TaskDto taskDto) {
        return null;
    }

    public static HabitDtoMapper getInstance() {
        if (INSTANCE == null) {
            synchronized (HabitDtoMapper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HabitDtoMapper();
                }
            }
        }
        return INSTANCE;
    }
}
