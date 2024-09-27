package com.pulsar.habitica.servlet;

import com.pulsar.habitica.filter.GuestPaths;
import com.pulsar.habitica.filter.PublicPaths;
import com.pulsar.habitica.util.LocaleUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Locale;

@WebServlet("/locale")
public class LocaleServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var selectedLanguage = request.getParameter("lang");
        var userLocale = request.getLocale();
        var locale = (selectedLanguage == null) ? userLocale.toString() : selectedLanguage;

        request.getSession().setAttribute(SessionAttribute.LANGUAGE.getValue(), locale);

        var prevPage = request.getHeader("referer");
        var page = (prevPage != null) ? prevPage : GuestPaths.LOGIN.getPath();
        response.sendRedirect(page);
    }

    @Override
    public void destroy() {

    }
}
