package com.pulsar.habitica.mapper;

import com.pulsar.habitica.annotation.MapperTest;
import com.pulsar.habitica.annotation.SingletonTest;
import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.entity.task.Complexity;

import static org.assertj.core.api.Assertions.assertThat;

public class HabitDtoMapperTest {

    private static final TaskDto TASK_DTO = TaskDto.builder()
            .userId(12)
            .heading("HABIT HEADING")
            .build();

    @MapperTest
    void successfulMapFromTaskDtoToHabit() {
        var mapper = HabitDtoMapper.getInstance();
        var habit = mapper.mapFrom(TASK_DTO);

        assertThat(habit).isNotNull();
        assertThat(habit.getUserId()).isEqualTo(TASK_DTO.getUserId());
        assertThat(habit.getHeading()).isEqualTo(TASK_DTO.getHeading());
        assertThat(habit.getComplexity()).isEqualTo(Complexity.EASY);
    }

    @SingletonTest
    void testSingletonInstance() {
        var instance1 = HabitDtoMapper.getInstance();
        var instance2 = HabitDtoMapper.getInstance();

        assertThat(instance1).isEqualTo(instance2);
    }
}
