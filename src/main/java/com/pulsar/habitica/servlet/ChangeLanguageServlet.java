package com.pulsar.habitica.servlet;

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
import java.util.Map;

@WebServlet("/change-language")
public class ChangeLanguageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var selectedLanguage = request.getSession().getAttribute("lang");
        var userLocale = request.getLocale();
        var locale = (selectedLanguage == null) ? userLocale.toString() : selectedLanguage;

        request.setAttribute("lang", new JSONObject(Map.of("lang", locale)));
        request.getRequestDispatcher(JspHelper.getPath("/change-language")).include(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject langData = ServletUtil.getJson(request);
        request.getSession().setAttribute("lang", langData.getString("lang"));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
