package com.pulsar.habitica.entity.task;


import java.time.LocalDate;

public class Task extends TaskBase {

    private LocalDate deadline;
    private Boolean status;

    private Task(TaskBuilder builder) {
        super(builder);
        this.deadline = builder.deadline;
        this.status = builder.status;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "deadline=" + deadline +
                ", status=" + status +
                ", id=" + id +
                ", heading='" + heading + '\'' +
                ", description='" + description + '\'' +
                ", complexity=" + complexity +
                ", userId=" + userId +
                '}';
    }

    public static class TaskBuilder extends TaskBase.Builder<TaskBuilder> {

        private LocalDate deadline;
        private Boolean status;

        private TaskBuilder() {}

        public TaskBuilder deadline(LocalDate deadline) {
            this.deadline = deadline;
            return self();
        }

        public TaskBuilder status(Boolean status) {
            this.status = status;
            return self();
        }

        @Override
        protected TaskBuilder self() {
            return this;
        }

        @Override
        public Task build() {
            return new Task(this);
        }
    }

    public static TaskBuilder builder() {
        return new TaskBuilder();
    }
}