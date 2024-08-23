package com.pulsar.habitica.servlet;

import com.pulsar.habitica.dao.user.UserDao;
import com.pulsar.habitica.dao.user.UserDaoImpl;
import com.pulsar.habitica.dto.LoginUserDto;
import com.pulsar.habitica.exception.ValidationException;
import com.pulsar.habitica.service.UserService;
import com.pulsar.habitica.util.JspHelper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        UserDao userDao = UserDaoImpl.getInstance();
        this.userService = new UserService(userDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JspHelper.getPath("login")).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

    @Override
    public void destroy() {

    }
}
