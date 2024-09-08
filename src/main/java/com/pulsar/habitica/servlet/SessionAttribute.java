package com.pulsar.habitica.servlet;

public enum SessionAttribute {

    USER("user"),
    USER_BALANCE("user_balance"),
    USER_IMAGE("user_image"),
    USER_STATISTICS("userStatistics"),
    TASKS("tasks"),
    LANGUAGE("lang"),
    ERRORS("errors");

    private final String value;

    SessionAttribute(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
