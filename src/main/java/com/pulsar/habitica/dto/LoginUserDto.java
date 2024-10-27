package com.pulsar.habitica.dto;

import java.util.Objects;

public class LoginUserDto {

    private final String identifier;
    private final String password;
    private final boolean isEmail;

    private LoginUserDto(String identifier, String password, boolean isEmail) {
        this.identifier = identifier;
        this.password = password;
        this.isEmail = isEmail;
    }

    public String getIdentifier() {
        return this.identifier;
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
        return isEmail == that.isEmail && Objects.equals(identifier, that.identifier) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, password, isEmail);
    }

    @Override
    public String toString() {
        return "LoginUserDto{" +
                "identifier='" + identifier + '\'' +
                ", password='" + password + '\'' +
                ", isEmail=" + isEmail +
                '}';
    }

    public static class LoginUserDtoBuilder {
        private String identifier;
        private String password;
        private boolean isEmail;

        private LoginUserDtoBuilder() {
        }

        public LoginUserDtoBuilder identifier(String identifier) {
            this.identifier = identifier;
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
            return new LoginUserDto(this.identifier, this.password, this.isEmail);
        }

        public String toString() {
            return "LoginUserDto.LoginUserDtoBuilder(identifier=" + this.identifier + ", password=" + this.password + ", isEmail=" + this.isEmail + ")";
        }
    }

    public static LoginUserDtoBuilder builder() {
        return new LoginUserDtoBuilder();
    }
}