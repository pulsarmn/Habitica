package com.pulsar.habitica.dto;

import java.util.Objects;

public class UserDto {

    private final Integer id;
    private final String nickname;
    private final String email;

    private UserDto(Integer id, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }

    public Integer getId() {
        return this.id;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(nickname, userDto.nickname) && Objects.equals(email, userDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, email);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static class UserDtoBuilder {

        private Integer id;
        private String nickname;
        private String email;

        private UserDtoBuilder() {
        }

        public UserDtoBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public UserDtoBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public UserDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDto build() {
            return new UserDto(this.id, this.nickname, this.email);
        }

        public String toString() {
            return "UserDto.UserDtoBuilder(id=" + this.id + ", nickname=" + this.nickname + ", email=" + this.email + ")";
        }
    }

    public static UserDtoBuilder builder() {
        return new UserDtoBuilder();
    }
}
