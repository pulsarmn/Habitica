package com.pulsar.habitica.mapper;

import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.entity.task.Complexity;
import com.pulsar.habitica.entity.task.DailyTask;

import java.time.LocalDate;

public class DailyTaskDtoMapper implements Mapper<TaskDto, DailyTask> {

    private static DailyTaskDtoMapper INSTANCE;

    @Override
    public DailyTask mapFrom(TaskDto taskDto) {
        return DailyTask.builder()
                .userId(taskDto.getUserId())
                .heading(taskDto.getHeading())
                .complexity(Complexity.EASY)
                .deadline(LocalDate.now())
                .series(0)
                .status(false)
                .build();
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
