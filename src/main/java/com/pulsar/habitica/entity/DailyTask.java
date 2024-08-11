package com.pulsar.habitica.entity;

import java.time.LocalDate;

public class DailyTask extends TaskBase {

    private LocalDate deadline;
    private Boolean status;
    private Integer series;

    private DailyTask(DailyTaskBuilder builder) {
        super(builder);
        this.deadline = builder.deadline;
        this.status = builder.status;
        this.series = builder.series;
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

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    @Override
    public String toString() {
        return "DailyTask{" +
                "deadline=" + deadline +
                ", status=" + status +
                ", series=" + series +
                ", id=" + id +
                ", heading='" + heading + '\'' +
                ", description='" + description + '\'' +
                ", complexity=" + complexity +
                ", userId=" + userId +
                '}';
    }

    public static class DailyTaskBuilder extends TaskBase.Builder<DailyTaskBuilder> {

        private LocalDate deadline;
        private Boolean status;
        private Integer series;

        private DailyTaskBuilder() {}

        public DailyTaskBuilder deadline(LocalDate deadline) {
            this.deadline = deadline;
            return self();
        }

        public DailyTaskBuilder status(Boolean status) {
            this.status = status;
            return self();
        }

        public DailyTaskBuilder series(Integer series) {
            this.series = series;
            return self();
        }

        @Override
        protected DailyTaskBuilder self() {
            return this;
        }

        @Override
        public TaskBase build() {
            return new DailyTask(this);
        }
    }

    public static DailyTaskBuilder builder() {
        return new DailyTaskBuilder();
    }
}