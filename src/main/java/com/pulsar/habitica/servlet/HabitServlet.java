package com.pulsar.habitica.servlet;

import com.pulsar.habitica.dao.task.HabitDaoImpl;
import com.pulsar.habitica.dto.TaskDto;
import com.pulsar.habitica.dto.UserDto;
import com.pulsar.habitica.entity.task.Habit;
import com.pulsar.habitica.service.HabitService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/habit")
public class HabitServlet extends HttpServlet {

    private HabitService habitService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var habitDao = HabitDaoImpl.getInstance();
        habitService = new HabitService(habitDao);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void destroy() {

    }

    private List<Habit> getHabitList(HttpServletRequest request) {
        var tempObject = request.getSession().getAttribute(SessionAttribute.HABITS.getValue());
        List<Habit> habits = new ArrayList<>();
        if (tempObject instanceof List<?> tempList) {
            if (!tempList.isEmpty() && tempList.get(0) instanceof Habit) {
                habits = (List<Habit>) tempList;
            }
        }
        return habits;
    }
}
