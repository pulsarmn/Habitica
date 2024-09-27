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
        var user = (UserDto) request.getSession().getAttribute(SessionAttribute.USER.getValue());
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        var userRewards = rewardService.findAllByUserId(user.getId());
        var id = request.getParameter("rewardId");

        int rewardId;
        try {
            rewardId = Integer.parseInt(id);
        }catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        var reward = userRewards.stream()
                .filter(userReward -> userReward.getId().equals(rewardId))
                .findFirst();

        if (reward.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        BigDecimal cost = reward.get().getCost();
        boolean isPurchased = userBalanceService.isPurchased(user.getId(), cost);

        if (isPurchased) {
            response.setStatus(HttpServletResponse.SC_OK);
        }else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
