package com.pulsar.habitica.servlet;

import com.pulsar.habitica.dao.reward.RewardDaoImpl;
import com.pulsar.habitica.dao.user.UserBalanceDaoImpl;
import com.pulsar.habitica.service.AwardRewardService;
import com.pulsar.habitica.service.RewardService;
import com.pulsar.habitica.service.UserBalanceService;
import com.pulsar.habitica.util.ServletUtil;
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
    private AwardRewardService awardRewardService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var userBalanceDao = UserBalanceDaoImpl.getInstance();
        userBalanceService = new UserBalanceService(userBalanceDao);
        var rewardDao = RewardDaoImpl.getInstance();
        rewardService = new RewardService(rewardDao);
        awardRewardService = new AwardRewardService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            var user = ServletUtil.getAuthenticatedUser(request);
            var rewardId = request.getParameter("rewardId");
            int id = Integer.parseInt(rewardId);
            var reward = rewardService.findById(id);

            BigDecimal cost = reward.getCost();
            boolean isPurchased = userBalanceService.isPurchased(user.getId(), cost);

            if (isPurchased) {
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }catch (Exception e) {
            ServletUtil.handleException(response, e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var user = ServletUtil.getAuthenticatedUser(request);
        var action = request.getParameter("action");
        var type = request.getParameter("type");
        var elementId = request.getParameter("id");
        int id = Integer.parseInt(elementId);

        BigDecimal balance = userBalanceService.findUserBalance(user.getId()).getBalance();
        BigDecimal rewardCost = awardRewardService.getRewardCost(type, id);

        if (action == null || action.equals("increment")) {
            balance = balance.add(rewardCost);
            userBalanceService.updateUserBalance(user.getId(), balance);
        }else if (action.equals("decrement")) {
            balance = balance.subtract(rewardCost);
            userBalanceService.updateUserBalance(user.getId(), balance);
        }
    }
}
