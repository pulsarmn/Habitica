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
        var user = (UserDto) request.getSession().getAttribute(SessionAttribute.USER.getValue());
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        List<DailyTask> dailyTasks = taskService.findAllByUserId(user.getId());

        request.setAttribute(SessionAttribute.DAILY_TASKS.getValue(), dailyTasks);
        request.getRequestDispatcher(JspHelper.getPath(PrivatePaths.DAILY_TASKS.getPath())).include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var dailyTaskHeading = request.getParameter("dailyTaskHeading");
        var user = (UserDto) request.getSession().getAttribute(SessionAttribute.USER.getValue());
        var taskDto = TaskDto.builder()
                .userId(user.getId())
                .heading(dailyTaskHeading)
                .build();
        var dailyTask = taskService.createDailyTask(taskDto);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var user = (UserDto) request.getSession().getAttribute(SessionAttribute.USER.getValue());
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        var id = request.getParameter("dailyTaskId");
        int dailyTaskId;
        try {
            dailyTaskId = Integer.parseInt(id);
        }catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        var result = taskService.deleteDailyTask(dailyTaskId);

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

    @Override
    public void destroy() {

    }

    private List<DailyTask> getDailyTaskList(HttpServletRequest request) {
        var tempObject = request.getSession().getAttribute(SessionAttribute.DAILY_TASKS.getValue());
        List<DailyTask> dailyTasks = new ArrayList<>();
        if (tempObject instanceof List<?> tempList) {
            if (!tempList.isEmpty() && tempList.get(0) instanceof DailyTask) {
                dailyTasks = (List<DailyTask>) tempList;
            }
        }
        return dailyTasks;
    }

    private String getAction(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (var bufferedReader = request.getReader()) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        }
        JSONObject json = new JSONObject(sb.toString());
        return json.getString("action");
    }

    private void doAction(String action, int dailyTaskId) {
        if (action.equals("increment")) {
            taskService.incrementSeries(dailyTaskId);
        }else if (action.equals("decrement")) {
            taskService.decrementSeries(dailyTaskId);
        }
    }
}
