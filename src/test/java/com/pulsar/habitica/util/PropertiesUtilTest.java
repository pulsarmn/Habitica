package com.pulsar.habitica.util;


import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class PropertiesUtilTest {

    @Test
    void successfulGettingTheProperty() {
        Map<String, String> map = Map.of(
                "db.url", "TEST:DB:URL",
                "db.username", "TEST:DB:USERNAME",
                "db.password", "TEST:DB:PASSWORD");


        assertAll("Checking the receipt of properties",
                map.entrySet().stream()
                        .map(entry ->
                                () -> assertThat(PropertiesUtil.get(entry.getKey()))
                                        .as("Checking property %s", entry.getKey())
                                        .isEqualTo(entry.getValue())
                        )
        );
    }
}
