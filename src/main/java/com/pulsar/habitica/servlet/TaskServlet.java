package com.pulsar.habitica.servlet;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var user = (UserDto) request.getSession().getAttribute(SessionAttribute.USER.getValue());
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        List<Task> tasks = taskService.findAllByUserId(user.getId());

        request.setAttribute(SessionAttribute.TASKS.getValue(), tasks);
        request.getRequestDispatcher(JspHelper.getPath(PrivatePaths.TASKS.getPath())).include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var taskHeading = request.getParameter("taskHeading");
        var user = (UserDto) request.getSession().getAttribute(SessionAttribute.USER.getValue());
        var taskDto = TaskDto.builder()
                .userId(user.getId())
                .heading(taskHeading)
                .build();
        var task = taskService.createTask(taskDto);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var user = (UserDto) request.getSession().getAttribute(SessionAttribute.USER.getValue());
        var id = request.getParameter("taskId");
        int taskId = id.matches("\\d+") ? Integer.parseInt(id) : 0;

        var result = taskService.deleteTask(taskId);
    }

    @Override
    public void destroy() {

    }

    private List<Task> getTaskList(HttpServletRequest request) {
        var tempObject = request.getSession().getAttribute(SessionAttribute.TASKS.getValue());
        List<Task> tasks = new ArrayList<>();
        if (tempObject instanceof List<?> tempList) {
            if (!tempList.isEmpty() && tempList.get(0) instanceof Task) {
                tasks = (List<Task>) tempList;
            }
        }
        return tasks;
    }
}
