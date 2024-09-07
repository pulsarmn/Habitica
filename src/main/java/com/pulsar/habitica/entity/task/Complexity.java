package com.pulsar.habitica.entity.task;

import java.util.Arrays;
import java.util.Optional;

public enum Complexity {

    TRIFLE,
    EASY,
    NORMALLY,
    DIFFICULT;

    public static Optional<Complexity> find(String name) {
        return Arrays.stream(values())
                .filter(complexity -> name.equals(complexity.name()))
                .findFirst();
    }
}
