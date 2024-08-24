package com.pulsar.habitica.filter;

public enum PrivatePaths {

    HOME("/");

    private String path;

    PrivatePaths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}