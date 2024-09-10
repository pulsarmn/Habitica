package com.pulsar.habitica.servlet;

public enum SessionAttribute {

    USER("user"),
    USER_BALANCE("userBalance"),
    USER_IMAGE("userImage"),
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
