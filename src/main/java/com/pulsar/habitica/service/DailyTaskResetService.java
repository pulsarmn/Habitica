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

    public void scheduleDailyReset() {
        long initialDelay = calculateInitialDelay();
        scheduler.scheduleAtFixedRate(this::resetDailyTasks, initialDelay, 1, TimeUnit.HOURS);
    }

    private long calculateInitialDelay() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextDayMidnight = now.toLocalDate().plusDays(1).atStartOfDay();
        return Duration.between(now, nextDayMidnight).toHours();
    }

    private void resetDailyTasks() {
        System.out.println("Сброс задач!!!");
        for (var dailyTask: dailyTaskService.findAll()) {
            dailyTask.setStatus(false);
            dailyTaskService.update(dailyTask);
        }
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
