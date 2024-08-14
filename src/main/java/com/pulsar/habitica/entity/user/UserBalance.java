package com.pulsar.habitica.entity.user;

import java.math.BigDecimal;
import java.util.Objects;

public class UserBalance {

    private Integer userId;
    private BigDecimal balance;

    private UserBalance(UserBalanceBuilder builder) {
        this.userId = builder.userId;
        this.balance = builder.balance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBalance that = (UserBalance) o;
        return Objects.equals(userId, that.userId) && Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, balance);
    }

    @Override
    public String toString() {
        return "UserBalance{" +
                "userId=" + userId +
                ", balance=" + balance +
                '}';
    }

    public static class UserBalanceBuilder {
        private Integer userId;
        private BigDecimal balance;

        UserBalanceBuilder() {
        }

        public UserBalanceBuilder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public UserBalanceBuilder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public UserBalance build() {
            return new UserBalance(this);
        }

        public String toString() {
            return "UserBalance.UserBalanceBuilder(userId=" + this.userId + ", balance=" + this.balance + ")";
        }
    }

    public static UserBalanceBuilder builder() {
        return new UserBalanceBuilder();
    }
}
