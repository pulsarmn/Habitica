package com.pulsar.habitica.dto;

import java.util.Objects;

public class TaskDto {

    private final Integer userId;
    private final String heading;

    private TaskDto(Integer userId, String heading) {
        this.userId = userId;
        this.heading = heading;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public String getHeading() {
        return this.heading;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDto taskDto = (TaskDto) o;
        return Objects.equals(userId, taskDto.userId) && Objects.equals(heading, taskDto.heading);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, heading);
    }

    @Override
    public String toString() {
        return "TaskDto{" +
                "userId=" + userId +
                ", heading='" + heading + '\'' +
                '}';
    }

    public static class TaskDtoBuilder {
        private Integer userId;
        private String heading;

        private TaskDtoBuilder() {
        }

        public TaskDtoBuilder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public TaskDtoBuilder heading(String heading) {
            this.heading = heading;
            return this;
        }

        public TaskDto build() {
            return new TaskDto(this.userId, this.heading);
        }

        public String toString() {
            return "TaskDto.TaskDtoBuilder(userId=" + this.userId + ", heading=" + this.heading + ")";
        }
    }

    public static TaskDtoBuilder builder() {
        return new TaskDtoBuilder();
    }
}
