package com.pulsar.habitica.filter;

public enum GuestPaths {

    LOGIN("/login"),
    REGISTER("/register");

    private String path;

    GuestPaths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
