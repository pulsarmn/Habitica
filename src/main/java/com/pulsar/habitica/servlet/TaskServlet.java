package com.pulsar.habitica.servlet;

import com.pulsar.habitica.dao.task.TaskDao;
import com.pulsar.habitica.dao.task.TaskDaoImpl;
import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.dto.UserDto;
import com.pulsar.habitica.entity.task.Complexity;
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
import java.time.LocalDate;
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
        var taskId = request.getParameter("id");
        if (taskId == null) {
            List<Task> tasks = taskService.findAllByUserId(user.getId());

            request.setAttribute(SessionAttribute.TASKS.getValue(), tasks);
            request.getRequestDispatcher(JspHelper.getPath(PrivatePaths.TASKS.getPath())).include(request, response);
        }else {
            int id;
            try {
                id = Integer.parseInt(taskId);
            }catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            var task = taskService.findById(id);
            request.setAttribute("task", task);
            request.setAttribute("taskData", new JSONObject(task));
            request.getRequestDispatcher(JspHelper.getPath("/task-modal-window")).include(request, response);
        }
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
        var id = request.getParameter("id");
        int taskId = id.matches("\\d+") ? Integer.parseInt(id) : 0;

        var result = taskService.deleteTask(taskId);
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
