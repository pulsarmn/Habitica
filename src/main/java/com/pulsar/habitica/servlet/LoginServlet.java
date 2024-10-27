package com.pulsar.habitica.servlet;

import com.pulsar.habitica.dao.user.UserDao;
import com.pulsar.habitica.dao.user.UserDaoImpl;
import com.pulsar.habitica.dto.LoginUserDto;
import com.pulsar.habitica.dto.ProfileUserDto;
import com.pulsar.habitica.exception.ValidationException;
import com.pulsar.habitica.filter.GuestPaths;
import com.pulsar.habitica.filter.PrivatePaths;
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
        request.getRequestDispatcher(JspHelper.getPath(GuestPaths.LOGIN.getPath())).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var loginUserDto = LoginUserDto.builder()
                .identifier(request.getParameter("identifier"))
                .password(request.getParameter("password"))
                .isEmail(request.getParameter("identifier").contains("@"))
                .build();
        try {
            var profileUser = userService.login(loginUserDto);
            addUserToSession(request, profileUser);
            response.sendRedirect(PrivatePaths.HOME.getPath());
        }catch (ValidationException exception) {
            request.setAttribute(ERRORS.getValue(), exception.getErrors());
            doGet(request, response);
        }
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
