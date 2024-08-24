package com.pulsar.habitica.filter;

public enum PublicPaths {

    ;

    private String path;

    PublicPaths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
