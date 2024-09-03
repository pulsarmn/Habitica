package com.pulsar.habitica.filter;

public enum PrivatePaths {

    HOME("/home"),
    UPLOAD_AVATAR("/upload-avatar");

    private String path;

    PrivatePaths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
