package com.pulsar.habitica.servlet;

import com.pulsar.habitica.dao.task.DailyTaskDaoImpl;
import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.entity.task.DailyTask;
import com.pulsar.habitica.filter.PrivatePaths;
import com.pulsar.habitica.service.DailyTaskService;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/daily-tasks")
public class DailyTaskServlet extends HttpServlet {

    private DailyTaskService taskService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var taskDao = DailyTaskDaoImpl.getInstance();
        taskService = new DailyTaskService(taskDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            var user = ServletUtil.getAuthenticatedUser(request);
            var dailyTaskId = request.getParameter("id");

            if (dailyTaskId == null) {
                processAllTasks(request, response, user.getId());
            }else {
                processSingleTask(request, response, dailyTaskId);
            }
        }catch (Exception e) {
            ServletUtil.handleException(response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var dailyTaskHeading = request.getParameter("entityHeading");
        var user = ServletUtil.getAuthenticatedUser(request);
        var taskDto = TaskDto.builder()
                .userId(user.getId())
                .heading(dailyTaskHeading)
                .build();
        taskService.createDailyTask(taskDto);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            var dailyTaskId = request.getParameter("id");
            int id = Integer.parseInt(dailyTaskId);
            taskService.deleteDailyTask(id);
        }catch (Exception e) {
            ServletUtil.handleException(response, e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            var updateParameter = request.getParameter("update");
            var dailyTaskId = request.getParameter("id");
            int id = Integer.parseInt(dailyTaskId);

            if (updateParameter == null) {
                var action = ServletUtil.getJson(request).getString("action");
                doAction(action, id);
            }else {
                var user = ServletUtil.getAuthenticatedUser(request);
                JSONObject dailyTaskData = ServletUtil.getJson(request);
                taskService.updateDailyTask(user.getId(), dailyTaskData);
            }
        }catch (Exception e) {
            ServletUtil.handleException(response, e);
        }
    }

    private void processAllTasks(HttpServletRequest request, HttpServletResponse response, int userId) throws ServletException, IOException {
        List<DailyTask> dailyTasks = taskService.findAllByUserId(userId);

        request.setAttribute(SessionAttribute.DAILY_TASKS.getValue(), dailyTasks);
        request.getRequestDispatcher(JspHelper.getPath(PrivatePaths.DAILY_TASKS.getPath())).include(request, response);
    }

    private void processSingleTask(HttpServletRequest request, HttpServletResponse response, String dailyTaskId) throws ServletException, IOException {
        int id = Integer.parseInt(dailyTaskId);
        var dailyTask = taskService.findById(id);

        request.setAttribute("dailyTask", dailyTask);
        request.setAttribute("dailyTaskData", new JSONObject(dailyTask));
        request.getRequestDispatcher(JspHelper.getPath("/daily-task-modal-window")).include(request, response);
    }

    private void doAction(String action, int dailyTaskId) {
        if (action.equals("increment")) {
            taskService.incrementSeries(dailyTaskId);
        }else if (action.equals("decrement")) {
            taskService.decrementSeries(dailyTaskId);
        }
    }
}
