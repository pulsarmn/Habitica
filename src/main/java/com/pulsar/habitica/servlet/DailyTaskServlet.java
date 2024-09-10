package com.pulsar.habitica.servlet;

import com.pulsar.habitica.dao.task.DailyTaskDaoImpl;
import com.pulsar.habitica.dao.task.TaskDao;
import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.dto.UserDto;
import com.pulsar.habitica.entity.task.DailyTask;
import com.pulsar.habitica.service.DailyTaskService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/daily-tasks")
public class DailyTaskServlet extends HttpServlet {

    private DailyTaskService taskService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var taskDao = DailyTaskDaoImpl.getInstance();
        taskService = new DailyTaskService(taskDao);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void destroy() {

    }
}
