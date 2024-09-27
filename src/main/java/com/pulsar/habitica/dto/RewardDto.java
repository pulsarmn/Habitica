package com.pulsar.habitica.dto;

import java.util.Objects;

public final class RewardDto {

    private final Integer userId;
    private final String heading;

    private RewardDto(Integer userId, String heading) {
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
        RewardDto rewardDto = (RewardDto) o;
        return Objects.equals(userId, rewardDto.userId) && Objects.equals(heading, rewardDto.heading);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, heading);
    }

    @Override
    public String toString() {
        return "RewardDto{" +
                "userId=" + userId +
                ", heading='" + heading + '\'' +
                '}';
    }

    public static class RewardDtoBuilder {
        private Integer userId;
        private String heading;

        private RewardDtoBuilder() {
        }

        public RewardDtoBuilder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public RewardDtoBuilder heading(String heading) {
            this.heading = heading;
            return this;
        }

        public RewardDto build() {
            return new RewardDto(this.userId, this.heading);
        }

        public String toString() {
            return "RewardDto.RewardDtoBuilder(userId=" + this.userId + ", heading=" + this.heading + ")";
        }
    }

    public static RewardDtoBuilder builder() {
        return new RewardDtoBuilder();
    }
}
