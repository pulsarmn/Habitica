package com.pulsar.habitica.filter;

public enum PrivatePaths {

    HOME("/home"),
    UPLOAD_AVATAR("/upload-avatar"),
    TASKS("/tasks"),
    HABITS("/habits"),
    DAILY_TASKS("/daily-tasks"),
    REWARDS("/rewards"),
    PURCHASE_REWARD("/purchase-reward"),
    USER_BALANCE("/balance"),
    LOG_OUT("/logout");

    private String path;

    PrivatePaths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
