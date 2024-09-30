package com.pulsar.habitica.entity.task;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

public enum Complexity {

    TRIFLE(new BigDecimal("0.7")),
    EASY(new BigDecimal("1")),
    NORMAL(new BigDecimal("1.5")),
    DIFFICULT(new BigDecimal("2.5"));

    private final BigDecimal cost;

    Complexity(BigDecimal cost) {
        this.cost = cost;
    }

    public static Optional<Complexity> find(String name) {
        return Arrays.stream(values())
                .filter(complexity -> name.equals(complexity.name()))
                .findFirst();
    }

    public BigDecimal getCost() {
        return cost;
    }
}
