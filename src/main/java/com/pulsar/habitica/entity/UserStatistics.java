package com.pulsar.habitica.entity;

import java.util.Objects;

public class UserStatistics {

    private Integer userId;
    private Integer totalVisits;

    private UserStatistics(UserStatisticsBuilder builder) {
        this.userId = builder.userId;
        this.totalVisits = builder.totalVisits;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTotalVisits() {
        return totalVisits;
    }

    public void setTotalVisits(Integer totalVisits) {
        this.totalVisits = totalVisits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStatistics that = (UserStatistics) o;
        return Objects.equals(userId, that.userId) && Objects.equals(totalVisits, that.totalVisits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, totalVisits);
    }

    @Override
    public String toString() {
        return "UserStatistics{" +
                "userId=" + userId +
                ", totalVisits=" + totalVisits +
                '}';
    }

    public static class UserStatisticsBuilder {
        private Integer userId;
        private Integer totalVisits;

        UserStatisticsBuilder() {
        }

        public UserStatisticsBuilder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public UserStatisticsBuilder totalVisits(Integer totalVisits) {
            this.totalVisits = totalVisits;
            return this;
        }

        public UserStatistics build() {
            return new UserStatistics(this);
        }

        public String toString() {
            return "UserStatistics.UserStatisticsBuilder(userId=" + this.userId + ", totalVisits=" + this.totalVisits + ")";
        }
    }

    public static UserStatisticsBuilder builder() {
        return new UserStatisticsBuilder();
    }
}
