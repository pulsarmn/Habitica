package com.pulsar.habitica.servlet;

import com.pulsar.habitica.dao.task.DailyTaskDaoImpl;
import com.pulsar.habitica.service.DailyTaskResetService;
import com.pulsar.habitica.service.DailyTaskService;
import com.pulsar.habitica.util.ConnectionManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    private DailyTaskResetService resetService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        var taskDao = DailyTaskDaoImpl.getInstance();
        var dailyTaskService = new DailyTaskService(taskDao);
        
        resetService = new DailyTaskResetService(dailyTaskService);
        resetService.scheduleDailyReset();
        System.out.println("Планировщик сброса задач запущен!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        resetService.shutdown();
        ConnectionManager.closePool();
        System.out.println("Работа приложения завершена!");
    }
}
