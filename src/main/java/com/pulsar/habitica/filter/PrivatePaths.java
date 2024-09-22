package com.pulsar.habitica.filter;

public enum PrivatePaths {

    HOME("/home"),
    UPLOAD_AVATAR("/upload-avatar"),
    TASKS("/tasks"),
    HABITS("/habit"),
    DAILY_TASKS("/daily-tasks"),
    REWARDS("/reward"),
    PURCHASE_REWARD("/purchase-reward"),
    USER_BALANCE("/balance");

    private String path;

    PrivatePaths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
