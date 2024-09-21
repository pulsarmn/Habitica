package com.pulsar.habitica.servlet;

import com.google.gson.Gson;
import com.pulsar.habitica.dao.user.UserBalanceDaoImpl;
import com.pulsar.habitica.dto.UserDto;
import com.pulsar.habitica.service.UserBalanceService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@WebServlet("/balance")
public class UserBalanceServlet extends HttpServlet {

    private UserBalanceService userBalanceService;
    private static final Gson gson = new Gson();

    @Override
    public void init(ServletConfig config) throws ServletException {
        var userBalanceDao = UserBalanceDaoImpl.getInstance();
        userBalanceService = new UserBalanceService(userBalanceDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var user = (UserDto) request.getSession().getAttribute(SessionAttribute.USER.getValue());
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        var userBalance = userBalanceService.findUserBalance(user.getId());
        if (userBalance == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        String balance = userBalance.getBalance().toString();
        String jsonResponse = gson.toJson(Map.of("balance", balance));

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }
}
