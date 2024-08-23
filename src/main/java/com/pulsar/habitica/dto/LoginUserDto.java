package com.pulsar.habitica.dto;

import java.util.Objects;

public class LoginUserDto {

    private final String idetifier;
    private final String password;
    private final boolean isEmail;

    private LoginUserDto(String idetifier, String password, boolean isEmail) {
        this.idetifier = idetifier;
        this.password = password;
        this.isEmail = isEmail;
    }

    public String getIdetifier() {
        return this.idetifier;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isEmail() {
        return this.isEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginUserDto that = (LoginUserDto) o;
        return isEmail == that.isEmail && Objects.equals(idetifier, that.idetifier) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idetifier, password, isEmail);
    }

    @Override
    public String toString() {
        return "LoginUserDto{" +
                "idetifier='" + idetifier + '\'' +
                ", password='" + password + '\'' +
                ", isEmail=" + isEmail +
                '}';
    }

    public static class LoginUserDtoBuilder {
        private String idetifier;
        private String password;
        private boolean isEmail;

        private LoginUserDtoBuilder() {
        }

        public LoginUserDtoBuilder idetifier(String idetifier) {
            this.idetifier = idetifier;
            return this;
        }

        public LoginUserDtoBuilder password(String password) {
            this.password = password;
            return this;
        }

        public LoginUserDtoBuilder isEmail(boolean isEmail) {
            this.isEmail = isEmail;
            return this;
        }

        public LoginUserDto build() {
            return new LoginUserDto(this.idetifier, this.password, this.isEmail);
        }

        public String toString() {
            return "LoginUserDto.LoginUserDtoBuilder(idetifier=" + this.idetifier + ", password=" + this.password + ", isEmail=" + this.isEmail + ")";
        }
    }

    public static LoginUserDtoBuilder builder() {
        return new LoginUserDtoBuilder();
    }
}