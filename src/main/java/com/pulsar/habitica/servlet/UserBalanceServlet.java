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

    @Override
    public void init(ServletConfig config) throws ServletException {
        var userBalanceDao = UserBalanceDaoImpl.getInstance();
        userBalanceService = new UserBalanceService(userBalanceDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}
