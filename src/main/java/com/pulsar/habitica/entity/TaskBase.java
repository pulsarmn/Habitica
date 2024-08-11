package com.pulsar.habitica.entity;


import java.util.Objects;

public abstract class TaskBase {

    protected Integer id;
    protected String heading;
    protected String description;
    protected Complexity complexity;
    protected Integer userId;

    protected TaskBase(Builder<?> builder) {
        this.id = builder.id;
        this.heading = builder.heading;
        this.description = builder.description;
        this.complexity = builder.complexity;
        this.userId = builder.userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Complexity getComplexity() {
        return complexity;
    }

    public void setComplexity(Complexity complexity) {
        this.complexity = complexity;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskBase taskBase = (TaskBase) o;
        return Objects.equals(id, taskBase.id) && Objects.equals(heading, taskBase.heading) && Objects.equals(description, taskBase.description) && complexity == taskBase.complexity && Objects.equals(userId, taskBase.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, heading, description, complexity, userId);
    }

    @Override
    public String toString() {
        return "TaskBase{" +
                "id=" + id +
                ", heading='" + heading + '\'' +
                ", description='" + description + '\'' +
                ", complexity=" + complexity +
                ", userId=" + userId +
                '}';
    }

    public static abstract class Builder<T extends Builder<T>> {

        private Integer id;
        private String heading;
        private String description;
        private Complexity complexity;
        private Integer userId;

        public T id(Integer id) {
            this.id = id;
            return self();
        }

        public T heading(String heading) {
            this.heading = heading;
            return self();
        }

        public T description(String description) {
            this.description = description;
            return self();
        }

        public T complexity(Complexity complexity) {
            this.complexity = complexity;
            return self();
        }

        public T userId(Integer userId) {
            this.userId = userId;
            return self();
        }

        protected abstract T self();
        public abstract TaskBase build();
    }
}
