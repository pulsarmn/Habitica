package com.pulsar.habitica.mapper;

import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.entity.task.DailyTask;

public class DailyTaskDtoMapper implements Mapper<TaskDto, DailyTask> {

    private static DailyTaskDtoMapper INSTANCE;

    @Override
    public DailyTask mapFrom(TaskDto object) {
        return null;
    }

    public static DailyTaskDtoMapper getInstance() {
        if (INSTANCE == null) {
            synchronized (DailyTaskDtoMapper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DailyTaskDtoMapper();
                }
            }
        }
        return INSTANCE;
    }
}
