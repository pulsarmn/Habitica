package com.pulsar.habitica.mapper;

import com.pulsar.habitica.annotation.MapperTest;
import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.entity.task.Complexity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskDtoMapperTest {

    private static final TaskDto TASK_DTO = TaskDto.builder()
            .userId(12)
            .heading("TASK HEADING")
            .build();

    @MapperTest
    void successfulMapFromTaskDtoToTask() {
        var mapper = TaskDtoMapper.getInstance();
        var task = mapper.mapFrom(TASK_DTO);

        assertThat(task).isNotNull();
        assertThat(task.getUserId()).isEqualTo(TASK_DTO.getUserId());
        assertThat(task.getHeading()).isEqualTo(TASK_DTO.getHeading());
        assertThat(task.getComplexity()).isEqualTo(Complexity.EASY);
        assertThat(task.getStatus()).isEqualTo(false);
    }

    @Test
    void testSingletonInstance() {
        var instance1 = TaskDtoMapper.getInstance();
        var instance2 = TaskDtoMapper.getInstance();

        assertThat(instance1).isEqualTo(instance2);
    }
}
