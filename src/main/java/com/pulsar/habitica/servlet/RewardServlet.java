package com.pulsar.habitica.servlet;

import com.pulsar.habitica.dao.reward.RewardDaoImpl;
import com.pulsar.habitica.dto.RewardDto;
import com.pulsar.habitica.dto.UserDto;
import com.pulsar.habitica.entity.Reward;
import com.pulsar.habitica.service.RewardService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/reward")
public class RewardServlet extends HttpServlet {

    private RewardService rewardService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var rewardDao = RewardDaoImpl.getInstance();
        rewardService = new RewardService(rewardDao);
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
}
