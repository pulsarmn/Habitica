package com.pulsar.habitica.servlet;

import com.pulsar.habitica.dao.task.TaskDao;
import com.pulsar.habitica.dao.task.TaskDaoImpl;
import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.entity.task.Task;
import com.pulsar.habitica.filter.PrivatePaths;
import com.pulsar.habitica.service.TaskService;
import com.pulsar.habitica.util.JspHelper;
import com.pulsar.habitica.util.ServletUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
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
        try {
            var user = ServletUtil.getAuthenticatedUser(request);
            var taskId = request.getParameter("id");

            if (taskId == null) {
                processAllTasks(request, response, user.getId());
            }else {
                processSingleTask(request, response, taskId);
            }
        }catch (Exception e) {
            ServletUtil.handleException(response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var taskHeading = request.getParameter("taskHeading");
        var user = ServletUtil.getAuthenticatedUser(request);
        var taskDto = TaskDto.builder()
                .userId(user.getId())
                .heading(taskHeading)
                .build();
        taskService.createTask(taskDto);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            var taskId = request.getParameter("id");
            int id = Integer.parseInt(taskId);
            taskService.deleteTask(id);
        }catch (Exception e) {
            ServletUtil.handleException(response, e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            var user = ServletUtil.getAuthenticatedUser(request);
            JSONObject jsonTask = ServletUtil.getJson(request);
            taskService.updateTask(user.getId(), jsonTask);
        }catch (Exception e) {
            ServletUtil.handleException(response, e);
        }
    }

    private void processAllTasks(HttpServletRequest request, HttpServletResponse response, int userId) throws ServletException, IOException {
        List<Task> tasks = taskService.findAllByUserId(userId);
        request.setAttribute(SessionAttribute.TASKS.getValue(), tasks);
        request.getRequestDispatcher(JspHelper.getPath(PrivatePaths.TASKS.getPath())).include(request, response);
    }

    private void processSingleTask(HttpServletRequest request, HttpServletResponse response, String taskId) throws ServletException, IOException {
        int id = Integer.parseInt(taskId);
        var task = taskService.findById(id);

        request.setAttribute("task", task);
        request.setAttribute("taskData", new JSONObject(task));
        request.getRequestDispatcher(JspHelper.getPath("/task-modal-window")).include(request, response);
    }
}
