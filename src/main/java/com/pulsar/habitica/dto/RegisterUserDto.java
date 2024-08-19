package com.pulsar.habitica.dto;

import java.util.Objects;

public class RegisterUserDto {

    private final String nickname;
    private final String email;
    private final String password;
    private final String doublePassword;

    RegisterUserDto(String nickname, String email, String password, String doublePassword) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.doublePassword = doublePassword;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getDoublePassword() {
        return this.doublePassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterUserDto that = (RegisterUserDto) o;
        return Objects.equals(nickname, that.nickname) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(doublePassword, that.doublePassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, email, password, doublePassword);
    }

    @Override
    public String toString() {
        return "RegisterUserDto{" +
                "nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", doublePassword='" + doublePassword + '\'' +
                '}';
    }

    public static class RegisterUserDtoBuilder {

        private String nickname;
        private String email;
        private String password;
        private String doublePassword;

        private RegisterUserDtoBuilder() {
        }

        public RegisterUserDtoBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public RegisterUserDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public RegisterUserDtoBuilder password(String password) {
            this.password = password;
            return this;
        }

        public RegisterUserDtoBuilder doublePassword(String doublePassword) {
            this.doublePassword = doublePassword;
            return this;
        }

        public RegisterUserDto build() {
            return new RegisterUserDto(this.nickname, this.email, this.password, this.doublePassword);
        }

        public String toString() {
            return "RegisterUserDto.RegisterUserDtoBuilder(nickname=" + this.nickname + ", email=" + this.email + ", password=" + this.password + ", doublePassword=" + this.doublePassword + ")";
        }
    }

    public static RegisterUserDtoBuilder builder() {
        return new RegisterUserDtoBuilder();
    }
}
