package com.pulsar.habitica.mapper;

import com.pulsar.habitica.annotation.MapperTest;
import com.pulsar.habitica.annotation.SingletonTest;
import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.entity.task.Complexity;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class DailyTaskDtoMapperTest {

    private static final TaskDto TASK_DTO = TaskDto.builder()
            .userId(12)
            .heading("DAILY TASK HEADING")
            .build();

    @MapperTest
    void successfulMapFromTaskDtoToDailyTask() {
        var mapper = DailyTaskDtoMapper.getInstance();
        var dailyTask = mapper.mapFrom(TASK_DTO);

        assertThat(dailyTask).isNotNull();
        assertThat(dailyTask.getUserId()).isEqualTo(TASK_DTO.getUserId());
        assertThat(dailyTask.getHeading()).isEqualTo(TASK_DTO.getHeading());
        assertThat(dailyTask.getComplexity()).isEqualTo(Complexity.EASY);
        assertThat(dailyTask.getDeadline()).isEqualTo(LocalDate.now());
        assertThat(dailyTask.getSeries()).isEqualTo(0);
        assertThat(dailyTask.getStatus()).isEqualTo(false);
    }

    @SingletonTest
    void testSingletonInstance() {
        var instance1 = DailyTaskDtoMapper.getInstance();
        var instance2 = DailyTaskDtoMapper.getInstance();

        assertThat(instance1).isEqualTo(instance2);
    }
}
