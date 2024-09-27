package com.pulsar.habitica.mapper;

import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.entity.task.Complexity;
import com.pulsar.habitica.entity.task.Task;

public class TaskDtoMapper implements Mapper<TaskDto, Task> {

    private static TaskDtoMapper INSTANCE;

    private TaskDtoMapper() {}

    @Override
    public Task mapFrom(TaskDto taskDto) {
        return Task.builder()
                .userId(taskDto.getUserId())
                .heading(taskDto.getHeading())
                .complexity(Complexity.EASY)
                .status(false)
                .build();
    }

    public static TaskDtoMapper getInstance() {
        if (INSTANCE == null) {
            synchronized (TaskDtoMapper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TaskDtoMapper();
                }
            }
        }
        return INSTANCE;
    }
}
