package com.pulsar.habitica.servlet;

import com.pulsar.habitica.dao.task.HabitDaoImpl;
import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.entity.task.Habit;
import com.pulsar.habitica.filter.PrivatePaths;
import com.pulsar.habitica.service.HabitService;
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

@WebServlet("/habits")
public class HabitServlet extends HttpServlet {

    private HabitService habitService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var habitDao = HabitDaoImpl.getInstance();
        habitService = new HabitService(habitDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            var user = ServletUtil.getAuthenticatedUser(request);
            var habitId = request.getParameter("id");

            if (habitId == null) {
                processAllHabits(request, response, user.getId());
            }else {
                processSingleHabits(request, response, habitId);
            }
        }catch (Exception e) {
            ServletUtil.handleException(response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var habitHeading = request.getParameter("habitHeading");
        var user = ServletUtil.getAuthenticatedUser(request);
        var habitDto = TaskDto.builder()
                .userId(user.getId())
                .heading(habitHeading)
                .build();
        habitService.createHabit(habitDto);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var action = getAction(request);
        var id = request.getParameter("habitId");
        int habitId = (id != null && id.matches("\\d+")) ? Integer.parseInt(id) : 0;

        doAction(action, habitId);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            var habitId = request.getParameter("id");
            int id = Integer.parseInt(habitId);
            habitService.deleteHabit(id);
        }catch (Exception e) {
            ServletUtil.handleException(response, e);
        }
    }

    private void processAllHabits(HttpServletRequest request, HttpServletResponse response, int userId) throws ServletException, IOException {
        List<Habit> habits = habitService.findAllByUserId(userId);

        request.setAttribute(SessionAttribute.HABITS.getValue(), habits);
        request.getRequestDispatcher(JspHelper.getPath(PrivatePaths.HABITS.getPath())).include(request, response);
    }

    private void processSingleHabits(HttpServletRequest request, HttpServletResponse response, String habitId) throws ServletException, IOException {
        int id = Integer.parseInt(habitId);
        var habit = habitService.findById(id);

        request.setAttribute("habit", habit);
        request.setAttribute("habitData", new JSONObject(habit));
        request.getRequestDispatcher(JspHelper.getPath("/habit-modal-window")).include(request, response);
    }

    private void doAction(String action, int habitId) {
        if (action.equals("increment")) {
            habitService.incrementGoodSeries(habitId);
        }else if (action.equals("decrement")) {
            habitService.decrementBadSeries(habitId);
        }
    }
}
