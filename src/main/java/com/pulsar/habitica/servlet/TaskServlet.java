package com.pulsar.habitica.servlet;

import com.google.gson.Gson;
import com.pulsar.habitica.dao.task.TaskDao;
import com.pulsar.habitica.dao.task.TaskDaoImpl;
import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.dto.UserDto;
import com.pulsar.habitica.entity.task.Task;
import com.pulsar.habitica.filter.PrivatePaths;
import com.pulsar.habitica.service.TaskService;
import com.pulsar.habitica.util.JspHelper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {

    private TaskService taskService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        TaskDao<Task> taskDao = TaskDaoImpl.getInstance();
        taskService = new TaskService(taskDao);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

    @Override
    public void destroy() {

    }

    private List<Task> getTaskList(HttpServletRequest request) {
        var tempObject = request.getSession().getAttribute("tasks");
        List<Task> tasks = new ArrayList<>();
        if (tempObject instanceof List<?> tempList) {
            if (!tempList.isEmpty() && tempList.get(0) instanceof Task) {
                tasks = (List<Task>) tempList;
            }
        }
        return tasks;
    }
}
