package com.pulsar.habitica.servlet;

import com.pulsar.habitica.dao.user.UserImageDao;
import com.pulsar.habitica.dao.user.UserImageDaoImpl;
import com.pulsar.habitica.dto.UserDto;
import com.pulsar.habitica.service.UserImageService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/upload-avatar")
@MultipartConfig
public class UploadAvatarServlet extends HttpServlet {

    private UserImageService userImageService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        UserImageDao userImageDao = UserImageDaoImpl.getInstance();
        userImageService = new UserImageService(userImageDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

    @Override
    public void destroy() {

    }
}
