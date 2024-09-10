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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var dailyTaskHeading = request.getParameter("dailyTaskHeading");
        var user = (UserDto) request.getSession().getAttribute(SessionAttribute.USER.getValue());
        var taskDto = TaskDto.builder()
                .userId(user.getId())
                .heading(dailyTaskHeading)
                .build();
        var dailyTask = taskService.createDailyTask(taskDto);
        var dailyTasksList = getDailyTaskList(request);

        dailyTasksList.add(0, dailyTask);
        request.getSession().setAttribute(SessionAttribute.DAILY_TASKS.getValue(), dailyTasksList);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
}
