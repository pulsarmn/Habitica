package com.pulsar.habitica.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DailyTaskResetService {

    private DailyTaskService dailyTaskService;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public DailyTaskResetService(DailyTaskService dailyTaskService) {
        this.dailyTaskService = dailyTaskService;
    }
}
