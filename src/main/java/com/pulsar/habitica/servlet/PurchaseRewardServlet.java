package com.pulsar.habitica.servlet;

import com.pulsar.habitica.dao.reward.RewardDaoImpl;
import com.pulsar.habitica.dao.user.UserBalanceDaoImpl;
import com.pulsar.habitica.dto.UserDto;
import com.pulsar.habitica.service.RewardService;
import com.pulsar.habitica.service.UserBalanceService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/purchase-reward")
public class PurchaseRewardServlet extends HttpServlet {

    private UserBalanceService userBalanceService;
    private RewardService rewardService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var userBalanceDao = UserBalanceDaoImpl.getInstance();
        userBalanceService = new UserBalanceService(userBalanceDao);
        var rewardDao = RewardDaoImpl.getInstance();
        rewardService = new RewardService(rewardDao);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
