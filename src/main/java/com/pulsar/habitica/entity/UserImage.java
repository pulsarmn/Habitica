package com.pulsar.habitica.entity;

import java.util.Objects;

public class UserImage {

    private Integer userId;
    private String imageAddr;

    private UserImage(UserImageBuilder builder) {
        this.userId = builder.userId;
        this.imageAddr = builder.imageAddr;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getImageAddr() {
        return imageAddr;
    }

    public void setImageAddr(String imageAddr) {
        this.imageAddr = imageAddr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserImage userImage = (UserImage) o;
        return Objects.equals(userId, userImage.userId) && Objects.equals(imageAddr, userImage.imageAddr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, imageAddr);
    }

    @Override
    public String toString() {
        return "UserImage{" +
                "userId=" + userId +
                ", imageAddr='" + imageAddr + '\'' +
                '}';
    }

    public static class UserImageBuilder {
        private Integer userId;
        private String imageAddr;

        UserImageBuilder() {
        }

        public UserImageBuilder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public UserImageBuilder imageAddr(String imageAddr) {
            this.imageAddr = imageAddr;
            return this;
        }

        public UserImage build() {
            return new UserImage(this);
        }

        public String toString() {
            return "UserImage.UserImageBuilder(userId=" + this.userId + ", imageAddr=" + this.imageAddr + ")";
        }
    }

    public static UserImageBuilder builder() {
        return new UserImageBuilder();
    }
}
