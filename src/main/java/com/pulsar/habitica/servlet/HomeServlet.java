package com.pulsar.habitica.servlet;

import com.pulsar.habitica.dao.user.UserDaoImpl;
import com.pulsar.habitica.dto.ProfileUserDto;
import com.pulsar.habitica.dto.UserDto;
import com.pulsar.habitica.service.UserService;
import com.pulsar.habitica.util.JspHelper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var userDao = UserDaoImpl.getInstance();
        userService = new UserService(userDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var user = (UserDto) request.getSession().getAttribute("user");
        var profileUserDto = userService.getProfileUserDto(user.getId());
        addUserToSession(request, profileUserDto);
        request.getRequestDispatcher(JspHelper.getPath("home")).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void addUserToSession(HttpServletRequest request, ProfileUserDto profileUser) {
        request.getSession().setAttribute("user", profileUser.getUserDto());
        request.getSession().setAttribute("userBalance", profileUser.getUserBalance());
        request.getSession().setAttribute("userImage", profileUser.getUserImage());
        request.getSession().setAttribute("userStatistics", profileUser.getUserStatistics());
    }

    @Override
    public void destroy() {

    }
}
