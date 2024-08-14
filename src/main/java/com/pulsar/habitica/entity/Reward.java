package com.pulsar.habitica.entity;


import java.math.BigDecimal;
import java.util.Objects;

public class Reward {

    private Integer id;
    private String heading;
    private String description;
    private BigDecimal cost;
    private Integer userId;

    private Reward(RewardBuilder builder) {
        this.id = builder.id;
        this.heading = builder.heading;
        this.description = builder.description;
        this.cost = builder.cost;
        this.userId = builder.userId;
    }

    public Integer getId() {
        return this.id;
    }

    public String getHeading() {
        return this.heading;
    }

    public String getDescription() {
        return this.description;
    }

    public BigDecimal getCost() {
        return this.cost;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reward reward = (Reward) o;
        return Objects.equals(id, reward.id) && Objects.equals(heading, reward.heading) && Objects.equals(description, reward.description) && Objects.equals(cost, reward.cost) && Objects.equals(userId, reward.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, heading, description, cost, userId);
    }

    @Override
    public String toString() {
        return "Reward{" +
                "id=" + id +
                ", heading='" + heading + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", userId=" + userId +
                '}';
    }

    public static class RewardBuilder {

        private Integer id;
        private String heading;
        private String description;
        private BigDecimal cost;
        private Integer userId;

        RewardBuilder() {
        }

        public RewardBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public RewardBuilder heading(String heading) {
            this.heading = heading;
            return this;
        }

        public RewardBuilder description(String description) {
            this.description = description;
            return this;
        }

        public RewardBuilder cost(BigDecimal cost) {
            this.cost = cost;
            return this;
        }

        public RewardBuilder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Reward build() {
            return new Reward(this);
        }

        public String toString() {
            return "Reward.RewardBuilder(id=" + this.id + ", heading=" + this.heading + ", description=" + this.description + ", cost=" + this.cost + ", userId=" + this.userId + ")";
        }
    }

    public static RewardBuilder builder() {
        return new RewardBuilder();
    }
}