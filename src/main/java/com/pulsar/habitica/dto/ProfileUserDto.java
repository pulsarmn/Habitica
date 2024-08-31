package com.pulsar.habitica.dto;

import com.pulsar.habitica.entity.user.UserBalance;
import com.pulsar.habitica.entity.user.UserImage;
import com.pulsar.habitica.entity.user.UserStatistics;

import java.util.Objects;

public class ProfileUserDto {

    private final UserDto userDto;
    private final UserBalance userBalance;
    private final UserImage userImage;
    private final UserStatistics userStatistics;

    private ProfileUserDto(UserDto userDto, UserBalance userBalance, UserImage userImage, UserStatistics userStatistics) {
        this.userDto = userDto;
        this.userBalance = userBalance;
        this.userImage = userImage;
        this.userStatistics = userStatistics;
    }

    public UserDto getUserDto() {
        return this.userDto;
    }

    public UserBalance getUserBalance() {
        return this.userBalance;
    }

    public UserImage getUserImage() {
        return this.userImage;
    }

    public UserStatistics getUserStatistics() {
        return this.userStatistics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileUserDto that = (ProfileUserDto) o;
        return Objects.equals(userDto, that.userDto) && Objects.equals(userBalance, that.userBalance) && Objects.equals(userImage, that.userImage) && Objects.equals(userStatistics, that.userStatistics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userDto, userBalance, userImage, userStatistics);
    }

    @Override
    public String toString() {
        return "ProfileUserDto{" +
                "userDto=" + userDto +
                ", userBalance=" + userBalance +
                ", userImage=" + userImage +
                ", userStatistics=" + userStatistics +
                '}';
    }

    public static class ProfileUserDtoBuilder {
        private UserDto userDto;
        private UserBalance userBalance;
        private UserImage userImage;
        private UserStatistics userStatistics;

        private ProfileUserDtoBuilder() {
        }

        public ProfileUserDtoBuilder userDto(UserDto userDto) {
            this.userDto = userDto;
            return this;
        }

        public ProfileUserDtoBuilder userBalance(UserBalance userBalance) {
            this.userBalance = userBalance;
            return this;
        }

        public ProfileUserDtoBuilder userImage(UserImage userImage) {
            this.userImage = userImage;
            return this;
        }

        public ProfileUserDtoBuilder userStatistics(UserStatistics userStatistics) {
            this.userStatistics = userStatistics;
            return this;
        }

        public ProfileUserDto build() {
            return new ProfileUserDto(this.userDto, this.userBalance, this.userImage, this.userStatistics);
        }

        public String toString() {
            return "ProfileUserDto.ProfileUserDtoBuilder(userDto=" + this.userDto + ", userBalance=" + this.userBalance + ", userImage=" + this.userImage + ", userStatistics=" + this.userStatistics + ")";
        }
    }

    public static ProfileUserDtoBuilder builder() {
        return new ProfileUserDtoBuilder();
    }
}
