package com.pulsar.habitica.servlet;

import com.pulsar.habitica.dao.task.DailyTaskDaoImpl;
import com.pulsar.habitica.dao.task.HabitDaoImpl;
import com.pulsar.habitica.dao.task.TaskDaoImpl;
import com.pulsar.habitica.dao.user.UserDaoImpl;
import com.pulsar.habitica.dto.ProfileUserDto;
import com.pulsar.habitica.dto.UserDto;
import com.pulsar.habitica.filter.PrivatePaths;
import com.pulsar.habitica.service.DailyTaskService;
import com.pulsar.habitica.service.HabitService;
import com.pulsar.habitica.service.TaskService;
import com.pulsar.habitica.service.UserService;
import com.pulsar.habitica.util.JspHelper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.pulsar.habitica.servlet.SessionAttribute.*;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private UserService userService;
    private TaskService taskService;
    private DailyTaskService dailyTaskService;
    private HabitService habitService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var userDao = UserDaoImpl.getInstance();
        userService = new UserService(userDao);
        var taskDao = TaskDaoImpl.getInstance();
        taskService = new TaskService(taskDao);
        var dailyTaskDao = DailyTaskDaoImpl.getInstance();
        dailyTaskService = new DailyTaskService(dailyTaskDao);
        var habitDao = HabitDaoImpl.getInstance();
        habitService = new HabitService(habitDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var user = (UserDto) request.getSession().getAttribute(USER.getValue());
        var profileUserDto = userService.getProfileUserDto(user.getId());
        var tasksList = taskService.findAllByUserId(user.getId());
        var dailyTasksList = dailyTaskService.findAllByUserId(user.getId());
        var habitsList = habitService.findAllByUserId(user.getId());

        request.getSession().setAttribute(TASKS.getValue(), tasksList);
        request.getSession().setAttribute(DAILY_TASKS.getValue(), dailyTasksList);
        request.getSession().setAttribute(HABITS.getValue(), habitsList);
        addUserToSession(request, profileUserDto);
        request.getRequestDispatcher(JspHelper.getPath(PrivatePaths.HOME.getPath())).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void addUserToSession(HttpServletRequest request, ProfileUserDto profileUser) {
        request.getSession().setAttribute(USER.getValue(), profileUser.getUserDto());
        request.getSession().setAttribute(USER_BALANCE.getValue(), profileUser.getUserBalance());
        request.getSession().setAttribute(USER_IMAGE.getValue(), profileUser.getUserImage());
        request.getSession().setAttribute(USER_STATISTICS.getValue(), profileUser.getUserStatistics());
    }

    @Override
    public void destroy() {

    }
}
